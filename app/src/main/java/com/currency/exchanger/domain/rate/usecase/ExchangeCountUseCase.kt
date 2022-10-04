package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.domain.rate.ExchangeLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangeCountUseCase  @Inject constructor(private val exchangeRepository: ExchangeLocalRepository) {
    suspend fun invoke() : Int {
        return exchangeRepository.exchangeCount()
    }
}