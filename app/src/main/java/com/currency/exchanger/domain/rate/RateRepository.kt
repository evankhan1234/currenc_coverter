package com.currency.exchanger.domain.rate

import com.currency.exchanger.data.common.utils.WrappedResponse
import com.currency.exchanger.data.rate.remote.dto.RateResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.rate.Entity.RateEntity
import kotlinx.coroutines.flow.Flow

interface RateRepository {
    suspend fun convert(amount:Double,from:String,to:String) : Flow<BaseResult<RateEntity, RateResponse>>
}