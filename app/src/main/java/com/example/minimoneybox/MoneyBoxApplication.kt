package com.example.minimoneybox

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.minimoneybox.Constants.FIVE_MINUTES
import com.example.minimoneybox.Constants.SP_STORAGE
import com.example.minimoneybox.Request.LoginRequest
import com.example.minimoneybox.api.MoneyBoxApiService
import com.example.minimoneybox.response.LoginResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MoneyBoxApplication : Application(), Application.ActivityLifecycleCallbacks {

    override fun onCreate() {
        super.onCreate()
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}

    override fun onActivityStarted(activity: Activity?) {

    }


    override fun onActivityStopped(activity: Activity?) {}

    override fun onActivityPaused(activity: Activity?) {}

    override fun onActivityResumed(activity: Activity?) {}

    override fun onActivityDestroyed(activity: Activity?) {}
}
