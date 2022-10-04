package com.currency.exchanger.domain.rate

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangeLocalRepository {
    suspend fun addExchange(exchange: Exchange)
    suspend fun exchangeCount() :Int
    suspend fun getExchangeList(): Flow<List<Exchange>>
}