package com.currency.exchanger.data.rate.remote.dto

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("rate") var rate: Double
)