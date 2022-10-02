package com.currency.exchanger.data.rate.remote.dto

import com.google.gson.annotations.SerializedName

data class ConvertRequest (
    @SerializedName("amount") var amount: Double,
    @SerializedName("from") var from: String,
    @SerializedName("to") var to: String,
)