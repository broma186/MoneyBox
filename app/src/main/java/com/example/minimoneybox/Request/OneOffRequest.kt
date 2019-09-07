package com.example.minimoneybox.Request

import com.example.minimoneybox.response.LoginResponse
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface OneOffApiService {

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("oneoffpayments")
    fun oneOffRequest(@Body oneOffRequest: OneOffRequest): Observable<ResponseBody>
}

data class OneOffRequest (
    @SerializedName("Amount") val status: Int = 0,
    @SerializedName("InvestorProductId") val investorProductId: Int = 0
)