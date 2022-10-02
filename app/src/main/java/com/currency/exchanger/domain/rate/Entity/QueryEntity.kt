package com.currency.exchanger.domain.rate.Entity

import com.google.gson.annotations.SerializedName

data class QueryEntity (
    var from: String,
    var to: String,
    var amount: Double,
)