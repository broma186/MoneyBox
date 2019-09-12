package com.example.minimoneybox.response

import com.google.gson.annotations.SerializedName



data class TopUpResponse (
    @SerializedName("Moneybox") var moneyBox: String? = null
)