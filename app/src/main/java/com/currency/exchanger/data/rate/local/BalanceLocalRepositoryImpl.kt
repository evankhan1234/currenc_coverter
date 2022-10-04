package com.currency.exchanger.data.rate.local

import com.currency.exchanger.data.rate.local.datasource.BalanceLocalDataSource
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BalanceLocalRepositoryImpl @Inject constructor(
    private val balanceLocalDataSource: BalanceLocalDataSource,
) :
    BalanceLocalRepository {


    override suspend fun addBalance(balance: Balance) {
        balanceLocalDataSource.addBalance(balance)
    }

    override suspend fun balanceSize(): Int {
        return balanceLocalDataSource.balanceSize()
    }

    override suspend fun getBalanceList(): Flow<List<Balance>> {
       return balanceLocalDataSource.getBalanceList()
    }
}