package com.currency.exchanger.data.currency.remote.dto

import com.google.gson.annotations.SerializedName

data class MotdResponse (
    @SerializedName("msg") var msg: String,
    @SerializedName("url") var url: String,
)