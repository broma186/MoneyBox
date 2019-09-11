package com.example.minimoneybox

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.minimoneybox.Constants.ANIMATED_FRACTION_MAX_CONSTANT
import com.example.minimoneybox.Constants.AUTH_TOKEN_KEY
import com.example.minimoneybox.Constants.AUTH_TOKEN_TIME_STAMP
import com.example.minimoneybox.Constants.BEARER_STR
import com.example.minimoneybox.Constants.EMAIL_REGEX
import com.example.minimoneybox.Constants.NAME_REGEX
import com.example.minimoneybox.Constants.PASSWORD_REGEX
import com.example.minimoneybox.Constants.PIG_ANIMATION_MAX_FRAME
import com.example.minimoneybox.Constants.PIG_ANIMATION_MIN_FRAME
import com.example.minimoneybox.Constants.PIG_SHOW_UP_FRAME
import com.example.minimoneybox.Constants.PLAN_VALUE_KEY
import com.example.minimoneybox.Constants.PRODUCT_RESPONSES_KEY
import com.example.minimoneybox.Constants.SP_STORAGE
import com.example.minimoneybox.Request.LoginRequest
import com.example.minimoneybox.Request.LoginRequestRealm
import com.example.minimoneybox.api.MoneyBoxApiService
import com.example.minimoneybox.response.InvestorResponse
import com.example.minimoneybox.response.LoginResponse
import com.example.minimoneybox.response.ProductResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Response
import okhttp3.ResponseBody
import java.time.LocalDateTime
import java.util.*
import java.util.regex.Pattern
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import kotlin.collections.ArrayList


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

    companion object {
        val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupViews()
    }

    override fun onStart() {
        super.onStart()
        setupAnimation()
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
            Toast.makeText(this, R.string.input_valid, Toast.LENGTH_LONG).show()
            MoneyBoxApiService.loginUser(this, LoginRequestRealm.getLoginRequest(et_email.text.toString(),
                et_password.text.toString(), et_name.text.toString()))
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

    // Starts the first part of the animation until the pig makes an appearance.
    private fun setupAnimation() {
        pigAnimation.setMaxFrame(PIG_SHOW_UP_FRAME) // Stop the animation when the pig shows up.
        pigAnimation.playAnimation()
        setupAnimationListener() // Respond to pig showing up, create next animation after.
    }

    /* Creates an animation listener that checks if the pig has showed up. If it has, let the money
     box pat it's pig forever until the activity restarts */
    private fun setupAnimationListener() {
        pigAnimation.addAnimatorUpdateListener({ animation ->
            if (animation.animatedFraction.compareTo(ANIMATED_FRACTION_MAX_CONSTANT) == 0) {
                pigAnimation.setMinAndMaxFrame(PIG_ANIMATION_MIN_FRAME, PIG_ANIMATION_MAX_FRAME)
                pigAnimation.repeatCount = LottieDrawable.INFINITE;
                pigAnimation.repeatMode = LottieDrawable.RESTART;
                pigAnimation.playAnimation()
            }
        })
    }



}


