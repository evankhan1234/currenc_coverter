package com.currency.exchanger.domain.currency

import com.currency.exchanger.data.currency.local.dto.Balance
import kotlinx.coroutines.flow.Flow

interface BalanceLocalRepository {
    suspend fun addBalance(balance: Balance)
    suspend fun updateBalance(available: Double,euroAvailable: Double, currencyName: String)
    suspend fun getBalanceList(): Flow<List<Balance>>
    suspend fun getBalance(currencyName: String): Balance
}