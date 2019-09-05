package com.example.minimoneybox.Models

import com.google.gson.annotations.SerializedName

class LoginSession {

    @SerializedName("BearerToken")
    var bearerToken: String? = null

    @SerializedName("ExternalSessionId")
    var sessionId: String? = null

    @SerializedName("SessionExternalId")
    var externalId: String? = null

    @SerializedName("ExpiryInSeconds")
    var expiryInSeconds: Int? = null


}