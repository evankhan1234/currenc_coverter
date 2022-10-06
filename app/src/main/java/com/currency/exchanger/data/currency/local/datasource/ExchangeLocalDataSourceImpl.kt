package com.currency.exchanger.data.currency.local.datasource

import com.currency.exchanger.data.currency.local.dao.ExchangeDao
import com.currency.exchanger.data.currency.local.dto.Exchange
import kotlinx.coroutines.flow.Flow

class ExchangeLocalDataSourceImpl (private val exchangeDao: ExchangeDao) : ExchangeLocalDataSource {
    override suspend fun addExchange(exchange: Exchange) {
        exchangeDao.addExchange(exchange)
    }

    override suspend fun exchangeCount(): Int {
       return exchangeDao.exchangeCount()
    }

    override suspend fun getExchangeList(): Flow<List<Exchange>> {
        return exchangeDao.getAllExchanges()
    }
}