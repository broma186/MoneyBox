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
import android.os.StrictMode
import android.widget.Toast
import com.example.minimoneybox.LoginActivity.Companion.TAG
import com.example.minimoneybox.Models.LoginResponse
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


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
        val retrofit = Retrofit.Builder()
            .baseUrl(LoginActivity.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(MoneyBoxService::class.java)

        val observable = service.loginUser(LoginRequest(LoginActivity.TEMP_EMAIL,
            LoginActivity.TEMP_PASSWORD, LoginActivity.TEMP_IDFA))
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ loginResponse ->
                assertTrue(loginResponse?.code() == 200)
            }, { error ->
                fail()
            })
    }
}
