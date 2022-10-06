package com.currency.exchanger.domain.currency.usecase

import com.currency.exchanger.domain.currency.BalanceLocalRepository
import javax.inject.Inject

class UpdateBalanceUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke(available: Double,euroAvailable: Double, currencyName: String) {
        return balanceLocalRepository.updateBalance(available, euroAvailable, currencyName)
    }
}