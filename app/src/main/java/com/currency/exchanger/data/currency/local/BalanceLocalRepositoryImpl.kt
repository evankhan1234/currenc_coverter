package com.currency.exchanger.data.currency.local

import com.currency.exchanger.data.currency.local.datasource.BalanceLocalDataSource
import com.currency.exchanger.data.currency.local.dto.Balance
import com.currency.exchanger.domain.currency.BalanceLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BalanceLocalRepositoryImpl @Inject constructor(
    private val balanceLocalDataSource: BalanceLocalDataSource,
) :
    BalanceLocalRepository {


    override suspend fun addBalance(balance: Balance) {
        balanceLocalDataSource.addBalance(balance)
    }

    override suspend fun updateBalance(available: Double,euroAvailable: Double, currencyName: String) {
        return balanceLocalDataSource.updateBalance(available, euroAvailable, currencyName)
    }

    override suspend fun getBalanceList(): Flow<List<Balance>> {
       return balanceLocalDataSource.getBalanceList()
    }

    override suspend fun getBalance(currencyName: String): Balance {
        return balanceLocalDataSource.getBalance(currencyName)
    }
}