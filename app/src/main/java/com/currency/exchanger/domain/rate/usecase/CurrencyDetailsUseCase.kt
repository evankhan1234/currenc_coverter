package com.currency.exchanger.domain.rate.usecase

import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.remote.dto.RateResponse
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.rate.BalanceLocalRepository
import com.currency.exchanger.domain.rate.Entity.RateEntity
import com.currency.exchanger.domain.rate.RateRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyDetailsUseCase @Inject constructor(private val balanceLocalRepository: BalanceLocalRepository) {
    suspend fun invoke(currencyName:String) : Balance{
        return balanceLocalRepository.getBalance(currencyName)
    }
}