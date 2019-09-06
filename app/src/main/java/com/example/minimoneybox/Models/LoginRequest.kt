package com.example.minimoneybox.Models

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("Email") var email: String,
    @SerializedName("Password") var password: String,
    @SerializedName("Idfa") var idfa: String
)