package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import javax.inject.Inject

class UpdateBalanceUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke(available: Double,euroAvailable: Double, currencyName: String) :Int {
        return balanceLocalRepository.updateBalance(available, euroAvailable, currencyName)
    }
}