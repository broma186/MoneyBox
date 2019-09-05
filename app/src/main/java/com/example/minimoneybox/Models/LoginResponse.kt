package com.example.minimoneybox.Models

import com.google.gson.annotations.SerializedName

class LoginResponse {

    @SerializedName("Session")
    var loginSession: LoginSession? = null


}