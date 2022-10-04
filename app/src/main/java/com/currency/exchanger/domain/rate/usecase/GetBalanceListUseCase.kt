package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceListUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke(): Flow<List<Balance>> {
        return balanceLocalRepository.getBalanceList()
    }
}