package com.currency.exchanger.data.rate.local.datasource

import com.currency.exchanger.data.rate.local.dao.BalanceDao
import com.currency.exchanger.data.rate.local.dto.Balance
import kotlinx.coroutines.flow.Flow

class BalanceLocalDataSourceImpl(private val balanceDao: BalanceDao) : BalanceLocalDataSource {
    override suspend fun addBalance(balance: Balance) {
        balanceDao.addBalances(balance)
    }

    override suspend fun updateBalance(available: Double,euroAvailable: Double, currencyName: String) {
        return balanceDao.updateBalance(available, euroAvailable, currencyName)
    }

    override suspend fun getBalanceList(): Flow<List<Balance>> {
        return balanceDao.getAllBalances()
    }

    override suspend fun getBalance(currencyName: String):Balance {
        return balanceDao.getBalance(currencyName)
    }
}