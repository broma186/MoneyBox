package com.example.minimoneybox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RelativeLayout
import com.example.minimoneybox.Request.LoginRequest
import com.example.minimoneybox.Request.LoginRequestRealm
import com.example.minimoneybox.api.MoneyBoxApiService

/*
    Displays splash screen and followed by the log in screen if user doesn't have a valid authentication token.
    Logs user in again regardless of having an expired auth token if re-running the app. This ensures that credentials
    are cached in the app without the necessity of local storage.
 */
class LaunchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        loginUserIfAuthExpiredOrNonExistent()
    }

    /*
    Takes user to log in screen if they haven't logged in before or if their authentication token has expired.
    If the user has logged in and they still have a valid auth token, log them in again with old credentials.
     */
    private fun loginUserIfAuthExpiredOrNonExistent() {
        val lastLoginTime =  getSharedPreferences(Constants.SP_STORAGE, Context.MODE_PRIVATE).getLong(Constants.AUTH_TOKEN_TIME_STAMP, 0)
        val timeMillisBetweenNowAndLastLogin : Long = System.currentTimeMillis() - lastLoginTime
        if (lastLoginTime > 0 && timeMillisBetweenNowAndLastLogin > Constants.FIVE_MINUTES) { // User has an invalid auth token. Take them to log in screen.
            goToLogin()
        }
        else if (lastLoginTime < 1) { // User never logged in, take user to login screen.
            goToLogin()
        }
        else { // Log user in again with old credentials from database as authentication hasn't expired yet.
            val loginRequestRealm : LoginRequestRealm = LoginRequestRealm.retrieveLoginRequestFromDatabase()
            MoneyBoxApiService.loginUser(this, LoginRequestRealm.getLoginRequest(loginRequestRealm.email,
                loginRequestRealm.password, loginRequestRealm.idfa))
        }
    }

    // Intent to take user to login screen.
    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}