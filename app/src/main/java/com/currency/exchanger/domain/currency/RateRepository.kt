package com.currency.exchanger.domain.currency

import com.currency.exchanger.data.currency.remote.dto.RateResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.currency.Entity.RateEntity
import kotlinx.coroutines.flow.Flow

interface RateRepository {
    suspend fun convert(amount:Double,from:String,to:String) : Flow<BaseResult<RateEntity, RateResponse>>
}