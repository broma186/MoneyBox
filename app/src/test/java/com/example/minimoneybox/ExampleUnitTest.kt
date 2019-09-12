package com.example.minimoneybox

import com.example.minimoneybox.Constants.TEMP_IDFA
import com.example.minimoneybox.Constants.TEMP_PASSWORD
import com.example.minimoneybox.Request.LoginRequestRealm
import io.realm.Realm
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    @Test
    fun writeLoginTestToDatabaseTest() {
        val realm: Realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        val loginRequestRealm: LoginRequestRealm = realm.createObject(LoginRequestRealm::class.java, Constants.TEMP_EMAIL)
        loginRequestRealm.password = TEMP_PASSWORD
        loginRequestRealm.idfa = TEMP_IDFA
        realm.copyToRealmOrUpdate(loginRequestRealm)
        realm.commitTransaction()


       assertTrue(Realm.getDefaultInstance().where(LoginRequestRealm::class.java)
           .equalTo(Constants.TEMP_EMAIL, Constants.TEMP_EMAIL)
           .findFirst().isValid)
    }

}
