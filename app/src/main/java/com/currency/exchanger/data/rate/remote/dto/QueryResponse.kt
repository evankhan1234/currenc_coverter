package com.currency.exchanger.data.rate.remote.dto

import com.google.gson.annotations.SerializedName

data class QueryResponse (
    @SerializedName("from") var from: String,
    @SerializedName("to") var to: String,
    @SerializedName("amount") var amount: Double,
)