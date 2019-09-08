package com.example.minimoneybox.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


class InvestorResponse() : Parcelable {

    @SerializedName("TotalPlanValue")
    var totalPlanValue: String? = null
    @SerializedName("ProductResponses")
    var productResponses: List<ProductResponse>? = null

    constructor(parcel: Parcel) : this() {
        totalPlanValue = parcel.readString()
        productResponses = parcel.createTypedArrayList(ProductResponse)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(totalPlanValue)
        parcel.writeTypedList(productResponses)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InvestorResponse> {
        override fun createFromParcel(parcel: Parcel): InvestorResponse {
            return InvestorResponse(parcel)
        }

        override fun newArray(size: Int): Array<InvestorResponse?> {
            return arrayOfNulls(size)
        }
    }
}


class ProductResponse() : Parcelable {

    @SerializedName("Id")
    var id: Int? = null

    @SerializedName("PlanValue")
    var planValue: String? = null

    @SerializedName("Moneybox")
    var moneyBox: String? = null

    @SerializedName("Product")
    var product: Product? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        planValue = parcel.readString()
        moneyBox = parcel.readString()
        product = parcel.readParcelable(Product::class.java!!.getClassLoader())

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(planValue)
        parcel.writeString(moneyBox)
        parcel.writeParcelable(product, 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProductResponse> {
        override fun createFromParcel(parcel: Parcel): ProductResponse {
            return ProductResponse(parcel)
        }

        override fun newArray(size: Int): Array<ProductResponse?> {
            return arrayOfNulls(size)
        }
    }
}

class Product() : Parcelable {
    @SerializedName("Id")
    var id: Int? = null
    @SerializedName("FriendlyName")
    var friendlyName: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        friendlyName = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(friendlyName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}