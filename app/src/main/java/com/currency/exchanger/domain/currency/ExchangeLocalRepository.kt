package com.currency.exchanger.domain.currency

import com.currency.exchanger.data.currency.local.dto.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangeLocalRepository {
    suspend fun addExchange(exchange: Exchange)
    suspend fun exchangeCount() :Int
    suspend fun getExchangeList(): Flow<List<Exchange>>
}