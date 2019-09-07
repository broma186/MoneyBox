package com.example.minimoneybox

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.minimoneybox.Request.LoginRequest
import com.example.minimoneybox.api.MoneyBoxApiService

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.adapter.rxjava2.Result.response
import com.google.gson.JsonObject




/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.minimoneybox", appContext.packageName)
    }

   /* @Test
    fun loginUser() {
        val loginRequest : LoginRequest = LoginRequest(Constants.TEMP_EMAIL,
            Constants.TEMP_PASSWORD, Constants.TEMP_IDFA)
        val observable = MoneyBoxApiService.loginApiCall().loginUser(loginRequest)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loginResponse ->
                assertTrue(loginResponse?.code() == 200)
                Log.d(LoginActivity.TAG, "login response recieved with code: " + loginResponse?.code())
                if (loginResponse?.code() == 200) {
                    val bearerToken : String? = loginResponse?.body()?.loginSession?.bearerToken
                    if (bearerToken != null) {
                        Log.d(LoginActivity.TAG, "loginUser bearer token is: " + bearerToken)
                        assertTrue(bearerToken != null)
                    }
                }
            }, { error ->
                fail()
                Log.d(LoginActivity.TAG,"login failure: " + error?.message)
            })
    }*/

    @Test
    fun getInvestorProducts() {
        val observable2 = MoneyBoxApiService.loginApiCall().loginUser(LoginRequest(Constants.TEMP_EMAIL,
            Constants.TEMP_PASSWORD, Constants.TEMP_IDFA))
        observable2.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loginResponse ->
               // Log.d(LoginActivity.TAG, "login response recieved with code: " + loginResponse?.code())
               // if (loginResponse?.code() == 200) {
                    val bearerToken : String? = loginResponse?.body()?.loginSession?.bearerToken
                    Log.d(LoginActivity.TAG, "loginUser bearer token is: " + bearerToken)
                if (bearerToken != null) {
                        val map : HashMap<String, String> = HashMap()
                        map.put("Authorization", "Bearer " + bearerToken)
                        map.put("AppId", "3a97b932a9d449c981b595")
                        map.put("Content-Type", "application/json")
                        map.put("appVersion",  "5.10.0")
                        map.put("apiVersion", "3.0.0")

                        val observable = MoneyBoxApiService.investorApiCall(bearerToken).getInvestorProducts(map)
                        observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({response: ResponseBody? ->
                                val post = JsonObject().get(response.toString()).asJsonObject
                                Log.d(LoginActivity.TAG,"Val of response body get investor: " + post)

                            }, { error ->
                                Log.d(LoginActivity.TAG,"get investors failure: " + error?.message)
                                fail()
                            })
                    }
               // }
            }, { error ->
                fail()
                Log.d(LoginActivity.TAG,"login failure: " + error?.message)
            })

    }
}
