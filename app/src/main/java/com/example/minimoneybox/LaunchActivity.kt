package com.example.minimoneybox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RelativeLayout
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
    Logs in user if they haven't logged in before or if their authentication token has expired.
    The user token expires after 5 minutes so user is logged in with old
    credentials and taken to user accounts screen in this case.
     */
    private fun loginUserIfAuthExpiredOrNonExistent() {
        val lastLoginTime =  getSharedPreferences(Constants.SP_STORAGE, Context.MODE_PRIVATE).getLong(Constants.AUTH_TOKEN_TIME_STAMP, 0)
        val timeMillisBetweenNowAndLastLogin : Long = System.currentTimeMillis() - lastLoginTime
        if (lastLoginTime > 0 || timeMillisBetweenNowAndLastLogin > Constants.FIVE_MINUTES) { // User has an invalid auth token.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        else { // Log user in again with old credentials.
            MoneyBoxApiService.loginUser(this)
        }
    }
}