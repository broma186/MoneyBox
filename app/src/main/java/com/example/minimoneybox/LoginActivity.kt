package com.example.minimoneybox

import android.content.Intent
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
import com.example.minimoneybox.Constants.ANIMATED_FRACTION_MAX_CONSTANT
import com.example.minimoneybox.Constants.EMAIL_REGEX
import com.example.minimoneybox.Constants.NAME_REGEX
import com.example.minimoneybox.Constants.PASSWORD_REGEX
import com.example.minimoneybox.Request.LoginRequest
import com.example.minimoneybox.api.MoneyBoxApiService
import com.example.minimoneybox.response.LoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.ResponseBody
import java.util.regex.Pattern



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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }



    override fun onStart() {
        super.onStart()
        setupAnimation()
    }

    companion object {
        val TAG = "LoginActivity"
    }

    private fun loginUser() {
        val loginRequest : LoginRequest = LoginRequest(Constants.TEMP_EMAIL,
            Constants.TEMP_PASSWORD, Constants.TEMP_IDFA)
        val observable = MoneyBoxApiService.loginApiCall().loginUser(loginRequest)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({loginResponse: LoginResponse? ->
                Log.d(TAG, "login response recieved")
                //TODO: If bearer token is different, store and use that instead.
                val bearerToken : String? = loginResponse?.loginSession?.bearerToken
                if (bearerToken != null) {
                    loadInvestorData(bearerToken, loginRequest)
                } else {
                    //TODO display toast saying user failed to login, They must input again.
                }
            }, { error ->
                Log.d(LoginActivity.TAG,"login failure: " + error?.message)
            })

    }

    private fun loadInvestorData(authToken : String?, loginRequest: LoginRequest) {

        Log.d(LoginActivity.TAG,"about to getInvestors")
        val observable = MoneyBoxApiService.investorApiCall(authToken).getInvestorProducts("Bearer " + authToken)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({response ->
                Log.d(LoginActivity.TAG,"getInvestor success, body is: " + response)


            }, { error ->
            Log.d(LoginActivity.TAG,"getinvestor failure: " + error?.message)
        })

    }


    private fun goToUserAccounts(loginRequest: LoginRequest) {
        val intent = Intent(this, UserAccountsActivity::class.java)
        intent.putExtra("email", loginRequest.email)
        intent.putExtra("password", loginRequest.password)
        if (!loginRequest.idfa.isEmpty()) {
            intent.putExtra("idfa", loginRequest.idfa)
        }
        startActivity(intent)
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


}


