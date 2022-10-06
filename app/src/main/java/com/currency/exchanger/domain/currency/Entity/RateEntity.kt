package com.currency.exchanger.domain.currency.Entity

data class RateEntity(
    var success: Boolean,
    var date: String,
    var result: Double,
    var query: QueryEntity,
    var info: InfoEntity,
)