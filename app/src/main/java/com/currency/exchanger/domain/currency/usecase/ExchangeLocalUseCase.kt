package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.data.currency.local.dto.Exchange
import com.currency.exchanger.domain.currency.ExchangeLocalRepository
import javax.inject.Inject

class ExchangeLocalUseCase  @Inject constructor(private val exchangeRepository: ExchangeLocalRepository) {
    suspend fun invoke(exchange: Exchange) {
        return exchangeRepository.addExchange(exchange)
    }
}