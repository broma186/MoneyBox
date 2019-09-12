package com.example.minimoneybox

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.util.Log
import android.widget.Toast
import com.example.minimoneybox.Constants.BEARER_STR
import com.example.minimoneybox.Request.LoginRequest
import com.example.minimoneybox.Request.TopUpRequest
import com.example.minimoneybox.api.MoneyBoxApiService
import com.example.minimoneybox.response.InvestorResponse
import com.example.minimoneybox.response.TopUpResponse

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

    @Test
    fun loginUser() {
        val loginRequest : LoginRequest = LoginRequest(Constants.TEMP_EMAIL,
            Constants.TEMP_PASSWORD, Constants.TEMP_IDFA)
        val observable = MoneyBoxApiService.loginApiCall().loginUser(loginRequest)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loginResponse ->
                val authToken : String? = loginResponse?.loginSession?.bearerToken
                if (authToken != null) {
                    assertTrue(authToken != null)
                }
            }, { error ->
                fail()
            })
    }

    @Test
    fun getInvestorProducts() {
        val observableInv = MoneyBoxApiService.investorApiCall().getInvestorProducts("Bearer TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=")
        observableInv.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({investorResponse: InvestorResponse? ->
                assertTrue(investorResponse?.totalPlanValue != null)
            }, { error -> })
    }

    @Test
    fun topUpPayment() {
        val observable = MoneyBoxApiService.topUpApiCall().topUp("Bearer TsMWRkbrcu3NGrpf84gi2+pg0iOMVymyKklmkY0oI84=", TopUpRequest(10, 1))
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ topUpResponse: TopUpResponse? ->
                assertTrue(topUpResponse != null)
            }, { error ->
                fail()
            })
    }
}
