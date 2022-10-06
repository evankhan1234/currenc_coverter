package com.currency.exchanger.data.currency.remote.dto

import com.google.gson.annotations.SerializedName

data class QueryResponse (
    @SerializedName("from") var from: String,
    @SerializedName("to") var to: String,
    @SerializedName("amount") var amount: Double,
)