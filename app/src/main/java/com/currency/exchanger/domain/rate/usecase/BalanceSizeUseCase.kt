package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import javax.inject.Inject

class BalanceSizeUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke() :Int {
        return balanceLocalRepository.balanceSize()
    }
}