package com.example.minimoneybox.Request

import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.Query


interface  InvestorApiService {


    @GET("/investorproducts")
    fun getInvestorProducts(@HeaderMap headers : Map<String, String>) : Observable<ResponseBody>
}
