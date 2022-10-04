package com.currency.exchanger.data.rate.local.datasource

import com.currency.exchanger.data.rate.local.dto.Exchange
import kotlinx.coroutines.flow.Flow

interface ExchangeLocalDataSource {
    suspend fun addExchange(exchange: Exchange)
    suspend fun exchangeCount() :Int
    suspend fun getExchangeList(): Flow<List<Exchange>>
}