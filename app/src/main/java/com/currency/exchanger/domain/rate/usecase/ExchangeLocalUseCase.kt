package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.domain.rate.ExchangeLocalRepository
import javax.inject.Inject

class ExchangeLocalUseCase  @Inject constructor(private val exchangeRepository: ExchangeLocalRepository) {
    suspend fun invoke(exchange: Exchange) {
        return exchangeRepository.addExchange(exchange)
    }
}