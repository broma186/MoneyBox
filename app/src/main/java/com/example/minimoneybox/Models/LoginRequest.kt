package com.example.minimoneybox.Models

import com.google.gson.annotations.SerializedName

class LoginRequest {

    @SerializedName("Email")
    var email: String? = null

    @SerializedName("Password")
    var sys: String? = null

    @SerializedName("Idfa")
    var idfa: String? = null

}