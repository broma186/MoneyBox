package com.example.minimoneybox.Interfaces

import com.example.minimoneybox.Models.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST



interface MoneyBoxService {


    @POST("users/login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<LoginRequest>

}