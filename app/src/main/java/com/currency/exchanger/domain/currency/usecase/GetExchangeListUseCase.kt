package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.data.currency.local.dto.Exchange
import com.currency.exchanger.domain.currency.ExchangeLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExchangeListUseCase  @Inject constructor(private val exchnageLocalRepository: ExchangeLocalRepository) {
    suspend fun invoke(): Flow<List<Exchange>> {
        return exchnageLocalRepository.getExchangeList()
    }
}