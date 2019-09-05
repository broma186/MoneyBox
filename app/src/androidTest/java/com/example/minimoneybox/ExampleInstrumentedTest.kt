package com.example.minimoneybox

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import com.example.minimoneybox.Interfaces.MoneyBoxService
import com.example.minimoneybox.Models.LoginRequest
import okhttp3.Interceptor
import okhttp3.OkHttpClient

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    class HeaderInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain) = chain.run {
            proceed(
                request()
                    .newBuilder()
                    .addHeader("AppId", "3a97b932a9d449c981b595")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("appVersion", "5.10.0")
                    .addHeader("apiVersion", "3.0.0")
                    .build()
            )
        }
    }


    @Test
    fun loginUser() {
        val client : OkHttpClient = OkHttpClient.Builder().addNetworkInterceptor(HeaderInterceptor()).build();
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        val service = retrofit.create<MoneyBoxService>(MoneyBoxService::class.java)

        val loginRequest : LoginRequest = LoginRequest()
        loginRequest.email = LoginActivity.TEMP_EMAIL
        loginRequest.password = LoginActivity.TEMP_PASSWORD
        loginRequest.idfa = LoginActivity.TEMP_IDFA

        val call = service.loginUser(loginRequest)
        call.enqueue(object : Callback<LoginRequest> {
            override fun onFailure(call: Call<LoginRequest>?, t: Throwable?) {
                Log.d(LoginActivity.TAG,"login failure")
            }

            override fun onResponse(call: Call<LoginRequest>?, response: Response<LoginRequest>?) {
                Log.d(LoginActivity.TAG,"login success")
                assertFalse(response == null)
                assertTrue(response?.code() == 200)
            }
        })
    }

}
