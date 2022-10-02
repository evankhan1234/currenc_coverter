package com.currency.exchanger.domain.rate.Entity

import com.currency.exchanger.data.rate.remote.dto.InfoResponse
import com.currency.exchanger.data.rate.remote.dto.QueryResponse

data class RateEntity(
    var success: Boolean,
    var date: String,
    var result: Double,
    var query: QueryEntity,
    var info: InfoEntity,
)