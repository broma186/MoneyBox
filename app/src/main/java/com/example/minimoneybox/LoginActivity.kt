package com.example.minimoneybox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.minimoneybox.Interfaces.MoneyBoxService
import com.example.minimoneybox.Models.LoginRequest
import com.example.minimoneybox.Models.LoginResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {


    lateinit var btn_sign_in : Button
    lateinit var til_email : TextInputLayout
    lateinit var et_email : EditText
    lateinit var til_password : TextInputLayout
    lateinit var et_password : EditText
    lateinit var til_name : TextInputLayout
    lateinit var et_name : EditText
    lateinit var pigAnimation : LottieAnimationView

    val service : MoneyBoxService? = null

    val ANIMATED_FRACTION_MAX_CONSTANT = 1.0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }



    override fun onStart() {
        super.onStart()
        setupAnimation()
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

    private fun loginUser() {
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
            }
        })
    }




    private fun setupViews() {
        btn_sign_in = findViewById(R.id.btn_sign_in)
        til_email = findViewById(R.id.til_email)
        et_email = findViewById(R.id.et_email)
        til_password = findViewById(R.id.til_password)
        et_password = findViewById(R.id.et_password)
        til_name = findViewById(R.id.til_name)
        et_name = findViewById(R.id.et_name)
        pigAnimation = findViewById(R.id.animation)

        btn_sign_in.setOnClickListener {
           // if (allFieldsValid()) {
            //    checkAndResetErrorWarnings()
                Toast.makeText(this, R.string.input_valid, Toast.LENGTH_LONG).show()
                loginUser()
          //  }
        }


    }



    private fun checkAndResetErrorWarnings() {
        if(!TextUtils.isEmpty(til_email.getError())) {
            til_email.setError(null)
        }
        if(!TextUtils.isEmpty(til_password.getError())) {
            til_password.setError(null)
        }
        if(!TextUtils.isEmpty(til_name.getError())) {
            til_name.setError(null)
        }
    }

    private fun allFieldsValid() : Boolean {
        var allValid = true

        if (!Pattern.matches(EMAIL_REGEX, et_email.text.toString())) {
            allValid = false
            til_email.error = getString(R.string.email_address_error)
        } else {
            til_email.error = null
        }

        if (!Pattern.matches(PASSWORD_REGEX, et_password.text.toString())) {
            allValid = false
            til_password.error = getString(R.string.password_error)
        } else {
            til_password.error = null
        }

        if (!Pattern.matches(NAME_REGEX, et_name.text.toString()) ||  et_name.text.toString().isEmpty()) {
            allValid = false
            til_name.error = getString(R.string.full_name_error)
        } else {
            til_name.error = null
        }

        return allValid
    }

    /* Creates an animation listener that checks if the pig has showed up. If it has, let the money
     box pat it's pig forever until the activity restarts */
    private fun setupAnimationListener() {
        pigAnimation.addAnimatorUpdateListener({ animation ->
            if (animation.animatedFraction.compareTo(ANIMATED_FRACTION_MAX_CONSTANT) == 0) {
                pigAnimation.setMinAndMaxFrame(131, 158)
                pigAnimation.repeatCount = LottieDrawable.INFINITE;
                pigAnimation.repeatMode = LottieDrawable.RESTART;
                pigAnimation.playAnimation()
            }
        })
    }



    private fun setupAnimation() {
        pigAnimation.setMaxFrame(109) // Stop the animation when the pig shows up.
        pigAnimation.playAnimation()
        setupAnimationListener() // Respond to pig showing up, create next animation after.
    }

    companion object {
        val EMAIL_REGEX = "[^@]+@[^.]+\\..+"
        val NAME_REGEX = "(.*?){0,30}"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z]).{10,50}$"
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158

        val BASE_URL : String = "https://api-test01.moneyboxapp.com/"
        val APP_ID : String = "AppId: 3a97b932a9d449c981b595"
        val CONTENT_TYPE : String = "application/json"
        val APP_VERSION : String = "5.10.0"
        val API_VERSION : String = "3.0.0"

        val TEMP_EMAIL : String = "androidtest@moneyboxapp.com"
        val TEMP_PASSWORD : String = "P455word12"
        val TEMP_IDFA : String = "YEEESSS"
        val TAG = "LoginActivity"

    }
}


