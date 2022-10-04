package com.currency.exchanger.presentation.main.currency_exchange

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Rate
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.rate.Entity.RateEntity
import com.currency.exchanger.domain.rate.usecase.*
import com.currency.exchanger.infra.utils.SomeUtils
import com.mynameismidori.currencypicker.ExtendedCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val convertUseCase: ConvertUseCase,
    private val exchangeLocalUseCase: ExchangeLocalUseCase,
    private val balanceLocalUseCase: BalanceLocalUseCase,
    private val getBalanceListUseCase: GetBalanceListUseCase,
    private val balanceSizeUseCase: BalanceSizeUseCase

    ) : ViewModel() {
    private val state =
        MutableStateFlow<CurrencyExchangeFragmentState>(CurrencyExchangeFragmentState.Init)
    val mState: StateFlow<CurrencyExchangeFragmentState> get() = state

    private val _firstRate = MutableStateFlow<RateEntity?>(null)
    val firstRate : StateFlow<RateEntity?> get() = _firstRate

    private val _secondRate = MutableStateFlow<RateEntity?>(null)
    val secondRate : StateFlow<RateEntity?> get() = _secondRate

    private fun setLoading() {
        state.value = CurrencyExchangeFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = CurrencyExchangeFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = CurrencyExchangeFragmentState.ShowToast(message)
    }

    private fun successCreate() {
        state.value = CurrencyExchangeFragmentState.SuccessCreate
    }

    fun convertFirstValue(amount: Double, from: String, to: String) {
        viewModelScope.launch {

         //  exchangeLocalUseCase.invoke(Rate(1,"USD","EUR",25.00,32.00,23.00,"2020-12-12"))

            convertUseCase.invoke(amount, from, to)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success ->  _firstRate.value = result.data
                        is BaseResult.Error -> showToast(result.rawResponse.toString())
                    }
                }
        }
    }
    fun convertSecondValue(amount: Double, from: String, to: String) {
        viewModelScope.launch {

            convertUseCase.invoke(amount, from, to)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    hideLoading()
                    when (result) {
                        is BaseResult.Success ->  _secondRate.value = result.data
                        is BaseResult.Error -> showToast(result.rawResponse.toString())
                    }
                }
        }
    }
    fun createBalance(){

        viewModelScope.launch {
            if (balanceSizeUseCase.invoke()>90){
                val currency = ExtendedCurrency.getAllCurrencies()
                for(cur in currency ){
                    if(cur.code.equals("EUR")){
                        balanceLocalUseCase.invoke(Balance(0,cur.code,cur.symbol,1000.00,1000.00,"2010-12-12"))
                    }
                    else{
                        balanceLocalUseCase.invoke(Balance(0,cur.code,cur.symbol,0.00,0.00,"2010-12-12"))
                    }

                }
            }


        }
    }

    fun exchange(fromAmount: Double,toAmount: Double,  fromCurrency: String, toCurrency: String){
        viewModelScope.launch {
            val size= balanceSizeUseCase.invoke()
            val item=size/10
            if (SomeUtils.isInteger(item.toDouble())) {
                exchangeLocalUseCase.invoke(Rate(0,fromCurrency,toCurrency,fromAmount,toAmount,0.00, SomeUtils.convertDateTime()))
            }
            else{
                exchangeLocalUseCase.invoke(Rate(0,fromCurrency,toCurrency,fromAmount,toAmount,0.70, SomeUtils.convertDateTime()))

            }


        }
    }
}

sealed class CurrencyExchangeFragmentState {
    object Init : CurrencyExchangeFragmentState()
    object SuccessCreate : CurrencyExchangeFragmentState()
    data class IsLoading(val isLoading: Boolean) : CurrencyExchangeFragmentState()
    data class ShowToast(val message: String) : CurrencyExchangeFragmentState()
}