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
import io.realm.Realm
import io.realm.RealmConfiguration




class MoneyBoxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder()
            .name("merealm.realm")
            .schemaVersion(1)
            .build()
        Realm.setDefaultConfiguration(config)
    }
}
