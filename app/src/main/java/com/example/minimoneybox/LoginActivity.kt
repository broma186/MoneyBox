package com.example.minimoneybox

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
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
            if (allFieldsValid()) {
                checkAndResetErrorWarnings()
                Toast.makeText(this, R.string.input_valid, Toast.LENGTH_LONG).show()
            }
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

    private fun setupAnimation() {
        pigAnimation.playAnimation()
    }

    companion object {
        val EMAIL_REGEX = "[^@]+@[^.]+\\..+"
        val NAME_REGEX = "(.*?){0,30}"
        val PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-zA-Z]).{10,50}$"
        val firstAnim = 0 to 109
        val secondAnim = 131 to 158
    }
}
