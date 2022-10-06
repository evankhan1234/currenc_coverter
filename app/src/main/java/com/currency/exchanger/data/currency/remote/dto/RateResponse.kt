package com.currency.exchanger.data.currency.remote.dto

import com.google.gson.annotations.SerializedName

data class RateResponse(
    @SerializedName("motd") var motd: MotdResponse,
    @SerializedName("success") var success: Boolean,
    @SerializedName("historical") var historical: Boolean,
    @SerializedName("date") var date: String,
    @SerializedName("result") var result: Double,
    @SerializedName("query") var query: QueryResponse,
    @SerializedName("info") var info: InfoResponse,
)