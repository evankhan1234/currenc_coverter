package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.domain.currency.ExchangeLocalRepository
import javax.inject.Inject

class ExchangeCountUseCase  @Inject constructor(private val exchangeRepository: ExchangeLocalRepository) {
    suspend fun invoke() : Int {
        return exchangeRepository.exchangeCount()
    }
}