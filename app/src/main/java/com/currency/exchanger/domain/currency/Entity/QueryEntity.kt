package com.currency.exchanger.domain.currency.Entity

data class QueryEntity (
    var from: String,
    var to: String,
    var amount: Double,
)