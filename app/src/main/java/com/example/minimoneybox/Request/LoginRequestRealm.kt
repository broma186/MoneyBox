package com.example.minimoneybox.Request

import com.example.minimoneybox.Constants
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class LoginRequestRealm : RealmObject() {
    @PrimaryKey
    var email: String? = null
    var password: String? = null
    var idfa: String? = null


    companion object {

        /* Takes a LoginRequest POJO as a parameter and writes it to the database as
            a LoginRequestRealm object.*/
        fun writeLoginRequestToDatabase(loginRequest: LoginRequest) {
            val realm: Realm = Realm.getDefaultInstance()
            realm.beginTransaction()
            val loginRequestRealm: LoginRequestRealm = realm.createObject(LoginRequestRealm::class.java, Constants.TEMP_EMAIL)
            loginRequestRealm.password = loginRequest.password
            loginRequestRealm.idfa = loginRequest.idfa
            realm.copyToRealmOrUpdate(loginRequestRealm)
            realm.commitTransaction()
        }

        /*
            Gets a login request from using the specified primary key
         */
        fun retrieveLoginRequestFromDatabase() : LoginRequestRealm? {
            return Realm.getDefaultInstance().where(LoginRequestRealm::class.java)
                .equalTo("email", Constants.TEMP_EMAIL)
                .findFirst()
        }

        /*
         Creates and returns a POJO using the given credentials.
        */
        fun getLoginRequest(email : String?, password : String?, idfa : String?) : LoginRequest {
            var loginRequest : LoginRequest = LoginRequest()
            loginRequest.email = email
            loginRequest.password = password
            loginRequest.idfa = idfa
            return loginRequest
        }
    }

}
