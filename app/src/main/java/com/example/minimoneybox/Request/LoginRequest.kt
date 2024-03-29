package com.example.minimoneybox.Request

import com.example.minimoneybox.Constants
import com.example.minimoneybox.response.LoginResponse
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import retrofit2.http.*

interface LoginApiService {
    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("users/login")
    fun loginUser(@Body loginRequest: LoginRequest): Observable<LoginResponse>
}

data class LoginRequest (
    @SerializedName("Email") var email: String? = null,
    @SerializedName("Password") var password: String? = null,
    @SerializedName("Idfa") var idfa: String? = null

)



