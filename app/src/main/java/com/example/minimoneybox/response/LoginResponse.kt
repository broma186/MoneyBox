package com.example.minimoneybox.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("Session") var loginSession: LoginSession? = null
)