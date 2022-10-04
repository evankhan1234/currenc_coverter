package com.currency.exchanger.domain.rate

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Rate
import kotlinx.coroutines.flow.Flow

interface BalanceLocalRepository {
    suspend fun addBalance(balance: Balance)
    suspend fun  balanceSize() :Int
    suspend fun getBalanceList(): Flow<List<Balance>>
}