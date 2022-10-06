package com.currency.exchanger.data.currency.remote.dto

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("rate") var rate: Double
)