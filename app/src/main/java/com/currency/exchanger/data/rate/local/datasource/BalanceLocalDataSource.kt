package com.currency.exchanger.data.rate.local.datasource

import com.currency.exchanger.data.rate.local.dto.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceLocalDataSource {
    suspend fun addBalance(balance: Balance)
    suspend fun updateBalance(available: Double,euroAvailable: Double, currencyName: String) :Int
    suspend fun getBalanceList(): Flow<List<Balance>>
    suspend fun getBalance(currencyName: String): Flow<Balance>

}