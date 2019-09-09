package com.example.minimoneybox.Request

import com.example.minimoneybox.response.TopUpResponse
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface TopUpApiService {

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @POST("oneoffpayments")
    fun topUp(@Query("Authorization") authorizationKey: String?, @Body topUpRequest: TopUpRequest): Observable<TopUpResponse>
}

data class TopUpRequest (
    @SerializedName("Amount") val status: Int? = 0,
    @SerializedName("InvestorProductId") val investorProductId: Int? = 0
)