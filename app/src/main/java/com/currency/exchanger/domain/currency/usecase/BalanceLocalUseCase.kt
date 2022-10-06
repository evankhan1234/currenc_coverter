package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.data.currency.local.dto.Balance
import com.currency.exchanger.domain.currency.BalanceLocalRepository
import javax.inject.Inject

class BalanceLocalUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke(balance: Balance) {
        return balanceLocalRepository.addBalance(balance)
    }
}