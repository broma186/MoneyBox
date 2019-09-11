package com.example.minimoneybox.api

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.minimoneybox.Constants
import com.example.minimoneybox.Request.*
import com.example.minimoneybox.UserAccountsActivity
import com.example.minimoneybox.response.InvestorResponse
import com.example.minimoneybox.response.LoginResponse
import com.google.gson.GsonBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import kotlin.math.log


object MoneyBoxApiService {

    private var mClient: OkHttpClient? = null
    private var logClient: OkHttpClient? = null
    private var mGsonConverter: GsonConverterFactory? = null
    private val authToken : String? = null

    fun loginApiCall() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(gsonConverter)
        .client(mainClient)
        .build()
        .create(LoginApiService::class.java)!!

    fun investorApiCall() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(gsonConverter)
        .client(mainClient)
        .build()
        .create(InvestorApiService::class.java)!!

    fun topUpApiCall() = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(gsonConverter)
        .client(mainClient)
        .build()
        .create(TopUpApiService::class.java)!!


    fun loginUser(context: Context, loginRequest: LoginRequest) {

        val observable = MoneyBoxApiService.loginApiCall().loginUser(loginRequest)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({loginResponse: LoginResponse? ->
                if (loginResponse?.loginSession != null) {
                    // Only write successful realm user credentials to database if that user doesn't exist.
                    if (LoginRequestRealm.retrieveLoginRequestFromDatabase() == null) {
                        LoginRequestRealm.writeLoginRequestToDatabase(loginRequest)
                    }
                    storeAuthTimeStamp(context) // Used for checking expired auth token.
                    loadInvestorData(context,Constants.BEARER_STR + loginResponse?.loginSession?.bearerToken, loginRequest)
                } else{
                    val msg = "Failed to log in " + loginRequest.idfa
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->

                val msg = "Failed to log in " + loginRequest.idfa
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            })

    }

    private fun storeAuthTimeStamp(context: Context) {
        context.getSharedPreferences(Constants.SP_STORAGE, Context.MODE_PRIVATE).edit().putLong(
            Constants.AUTH_TOKEN_TIME_STAMP, System.currentTimeMillis()).commit()
    }


    private fun loadInvestorData(context: Context, authToken : String, loginRequest: LoginRequest) {
        val observable = MoneyBoxApiService.investorApiCall().getInvestorProducts(authToken)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({investorResponse: InvestorResponse? ->
                if (investorResponse?.totalPlanValue != null) {
                    goToUserAccounts(context, authToken, loginRequest, investorResponse)
                } else {
                    val msg = loginRequest.idfa + " has no accounts"
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                }
            }, { error ->
            })

    }

    private fun goToUserAccounts(context: Context, authToken : String, loginRequest: LoginRequest, investorResponse: InvestorResponse) {
        val intent = Intent(context, UserAccountsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        intent.putExtra("email", loginRequest.email)
        intent.putExtra("password", loginRequest.password)
        if (!loginRequest.idfa?.isEmpty()!!) {
            intent.putExtra("idfa", loginRequest.idfa)
        }
        intent.putExtra(Constants.AUTH_TOKEN_KEY, authToken)
        intent.putExtra(Constants.PLAN_VALUE_KEY, investorResponse.totalPlanValue)
        intent.putParcelableArrayListExtra(Constants.PRODUCT_RESPONSES_KEY, ArrayList(investorResponse.productResponses))
        context.startActivity(intent)
    }

    val mainClient : OkHttpClient
        @Throws(NoSuchAlgorithmException::class, KeyManagementException::class)
        get() {
            if (mClient == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val httpBuilder = OkHttpClient.Builder()
                httpBuilder
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(interceptor)  /// show all JSON in logCat
                mClient = httpBuilder.build()

            }
            return mClient!!
        }

    val gsonConverter: GsonConverterFactory
        get() {
            if(mGsonConverter == null){
                mGsonConverter = GsonConverterFactory
                    .create(
                        GsonBuilder()
                            .setLenient()
                            .disableHtmlEscaping()
                            .create())
            }
            return mGsonConverter!!
        }

}

