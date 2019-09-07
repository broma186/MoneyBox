package com.example.minimoneybox.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @SerializedName("Session") var loginSession: LoginSession? = null
)

data class LoginSession (

    @SerializedName("BearerToken")
    var bearerToken: String? = null,

    @SerializedName("ExternalSessionId")
    var sessionId: String? = null,

    @SerializedName("SessionExternalId")
    var externalId: String? = null,

    @SerializedName("ExpiryInSeconds")
    var expiryInSeconds: Int? = null


)