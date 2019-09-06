package com.example.minimoneybox.Models

import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApiService {
    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("users/login")
    fun loginUser(@Body loginRequest: LoginRequest): Observable<Response<LoginResponse>>
}

data class LoginRequest (
    @SerializedName("Email") var email: String,
    @SerializedName("Password") var password: String,
    @SerializedName("Idfa") var idfa: String
)