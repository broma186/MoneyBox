package com.example.minimoneybox.response

import com.google.gson.annotations.SerializedName

data class InvestorResponse (

    @SerializedName("TotalPlanValue") val totalPlanValue: String? = null,
    @SerializedName("ProductResponses") val productResponses: List<ProductResponse>? = null
)

data class ProductResponse (

    @SerializedName("Id") val id: Int? = null,
    @SerializedName("PlanValue") val planValue: String? = null,
    @SerializedName("MoneyBox") val moneyBox: String? = null,
    @SerializedName("Product") val product: Product? = null
)

data class Product (
    @SerializedName("Id") val id: Int? = null,
    @SerializedName("FriendlyName") val friendlyName: String? = null
)