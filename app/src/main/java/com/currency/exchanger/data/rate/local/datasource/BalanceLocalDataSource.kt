package com.currency.exchanger.data.rate.local.datasource

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Rate
import kotlinx.coroutines.flow.Flow

interface BalanceLocalDataSource {
    suspend fun addBalance(balance: Balance)
    suspend fun balanceSize() :Int
    suspend fun getBalanceList(): Flow<List<Balance>>

}