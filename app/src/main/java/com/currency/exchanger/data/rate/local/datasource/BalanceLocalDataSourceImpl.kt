package com.currency.exchanger.data.rate.local.datasource

import com.currency.exchanger.data.rate.local.dao.BalanceDao
import com.currency.exchanger.data.rate.local.dto.Balance
import kotlinx.coroutines.flow.Flow

class BalanceLocalDataSourceImpl(private val balanceDao: BalanceDao) : BalanceLocalDataSource {
    override suspend fun addBalance(balance: Balance) {
        balanceDao.addBalances(balance)
    }

    override suspend fun balanceSize(): Int {
        return balanceDao.balanceSize()
    }

    override suspend fun getBalanceList(): Flow<List<Balance>> {
        return balanceDao.getAllBalances()
    }
}