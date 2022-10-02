package com.currency.exchanger.data.rate.remote.dto

import com.google.gson.annotations.SerializedName

data class MotdResponse (
    @SerializedName("msg") var msg: String,
    @SerializedName("url") var url: String,
)