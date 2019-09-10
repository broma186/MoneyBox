package com.example.minimoneybox

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.example.minimoneybox.Constants.FIVE_MINUTES
import com.example.minimoneybox.Constants.SP_STORAGE



class MoneyBoxApplication : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStarted(activity: Activity?) {
        val lastLogin =  getSharedPreferences(SP_STORAGE, Context.MODE_PRIVATE).getLong(Constants.AUTH_TOKEN_TIME_STAMP, 0)
        val timeMillisBetweenNowAndLastLogin : Long = System.currentTimeMillis() - lastLogin
        if (lastLogin > 0 && timeMillisBetweenNowAndLastLogin > FIVE_MINUTES) {
           val intent = Intent(this, LoginActivity::class.java)
           startActivity(intent)
        }
    }


    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}
}
