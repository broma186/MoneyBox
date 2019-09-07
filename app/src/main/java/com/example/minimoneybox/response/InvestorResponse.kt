package com.example.minimoneybox.response

import com.google.gson.annotations.SerializedName

data class InvestorResponse (

    @SerializedName("TotalPlanValue") var totalPlanValue: String? = null,
    @SerializedName("ProductResponses") var productResponses: ProductResponse? = null
)

data class ProductResponse (

    @SerializedName("Id") var id: Int? = null,
    @SerializedName("PlanValue") var planValue: String? = null,
    @SerializedName("MoneyBox") var moneyBox: String? = null,
    @SerializedName("Product") var product: Product? = null
)

data class Product (
    @SerializedName("Id") var id: Int? = null,
    @SerializedName("FriendlyName") var friendlyName: String? = null
)