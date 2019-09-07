package com.example.minimoneybox.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class InvestorResponse (

    @SerializedName("TotalPlanValue") val totalPlanValue: String? = null,
    @SerializedName("ProductResponses") val productResponses: List<ProductResponse>? = null
)

@Parcelize
class ProductResponse : Parcelable {

    @SerializedName("Id")
    val id: Int? = null

    @SerializedName("PlanValue")
    val planValue: String? = null

    @SerializedName("MoneyBox")
    val moneyBox: String? = null

    @SerializedName("Product")
    val product: Product? = null
}

data class Product (
    @SerializedName("Id") val id: Int? = null,
    @SerializedName("FriendlyName") val friendlyName: String? = null
)