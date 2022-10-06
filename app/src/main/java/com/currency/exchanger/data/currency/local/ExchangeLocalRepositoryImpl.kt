package com.currency.exchanger.data.currency.local

import com.currency.exchanger.data.currency.local.datasource.ExchangeLocalDataSource
import com.currency.exchanger.data.currency.local.dto.Exchange
import com.currency.exchanger.domain.currency.ExchangeLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExchangeLocalRepositoryImpl  @Inject constructor(
    private val exchangeLocalDataSource: ExchangeLocalDataSource,
) :
    ExchangeLocalRepository {

    override suspend fun addExchange(exchange: Exchange) {
        exchangeLocalDataSource.addExchange(exchange)
    }

    override suspend fun exchangeCount(): Int {
       return exchangeLocalDataSource.exchangeCount()
    }

    override suspend fun getExchangeList(): Flow<List<Exchange>> {
       return  exchangeLocalDataSource.getExchangeList()
    }

}