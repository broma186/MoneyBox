package com.example.minimoneybox.Request

import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query


interface  InvestorApiService {

    @Headers(
        "AppId: 3a97b932a9d449c981b595",
        "Content-Type: application/json",
        "appVersion: 5.10.0",
        "apiVersion: 3.0.0"
    )
    @GET("/investorproducts")
    fun getInvestorProducts(@Query("Authorization") authorizationKey: String) : Observable<ResponseBody>
}
