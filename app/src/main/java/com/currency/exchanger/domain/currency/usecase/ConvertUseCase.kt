package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.data.currency.remote.dto.RateResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.currency.Entity.RateEntity
import com.currency.exchanger.domain.currency.RateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConvertUseCase @Inject constructor(private val rateRepository: RateRepository) {
    suspend fun invoke(amount:Double,from:String,to:String) : Flow<BaseResult<RateEntity, RateResponse>> {
        return rateRepository.convert(amount,from, to)
    }
}