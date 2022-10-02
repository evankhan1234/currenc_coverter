package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Rate
import com.currency.exchanger.domain.rate.RateLocalRepository
import javax.inject.Inject

class ExchangeLocalUseCase  @Inject constructor(private val rateRepository: RateLocalRepository) {
    suspend fun invoke(rate: Rate) {
        return rateRepository.addRates(rate)
    }
}