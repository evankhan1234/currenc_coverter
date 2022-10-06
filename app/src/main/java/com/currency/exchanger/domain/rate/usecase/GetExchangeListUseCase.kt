package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import com.currency.exchanger.domain.rate.ExchangeLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeListUseCase  @Inject constructor(private val exchnageLocalRepository: ExchangeLocalRepository) {
    suspend fun invoke(): Flow<List<Exchange>> {
        return exchnageLocalRepository.getExchangeList()
    }
}