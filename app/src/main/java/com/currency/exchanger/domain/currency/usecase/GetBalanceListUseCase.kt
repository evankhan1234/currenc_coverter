package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.data.currency.local.dto.Balance
import com.currency.exchanger.domain.currency.BalanceLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceListUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke(): Flow<List<Balance>> {
        return balanceLocalRepository.getBalanceList()
    }
}