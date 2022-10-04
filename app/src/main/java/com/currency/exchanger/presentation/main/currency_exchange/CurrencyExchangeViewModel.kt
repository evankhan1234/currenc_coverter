package com.currency.exchanger.presentation.main.currency_exchange

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.rate.Entity.RateEntity
import com.currency.exchanger.domain.rate.usecase.*
import com.currency.exchanger.infra.utils.SomeUtils
import com.mynameismidori.currencypicker.ExtendedCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import javax.inject.Inject


@HiltViewModel
class CurrencyExchangeViewModel @Inject constructor(
    private val convertUseCase: ConvertUseCase,
    private val exchangeLocalUseCase: ExchangeLocalUseCase,
    private val balanceLocalUseCase: BalanceLocalUseCase,
    private val getBalanceListUseCase: GetBalanceListUseCase,
    private val updateBalanceUseCase: UpdateBalanceUseCase,
    private val exchangeCountUseCase: ExchangeCountUseCase,
    private val currencyDetailsUseCase: CurrencyDetailsUseCase

    ) : ViewModel() {
    private val df = DecimalFormat("00.00")
    private val state =
        MutableStateFlow<CurrencyExchangeFragmentState>(CurrencyExchangeFragmentState.Init)
    val mState: StateFlow<CurrencyExchangeFragmentState> get() = state

    private val _firstRate = MutableStateFlow<RateEntity?>(null)
    val firstRate : StateFlow<RateEntity?> get() = _firstRate

    private val _secondRate = MutableStateFlow<RateEntity?>(null)
    val secondRate : StateFlow<RateEntity?> get() = _secondRate


    private val _count = MutableStateFlow<Int?>(null)
    val count : StateFlow<Int?> get() = _count

    private val _euroRate = MutableStateFlow<Double?>(null)
    val euroRate : StateFlow<Double?> get() = _euroRate

    private fun setLoading() {
        state.value = CurrencyExchangeFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = CurrencyExchangeFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = CurrencyExchangeFragmentState.ShowToast(message)
    }
    private fun showCount(value: Int?) {
        state.value = CurrencyExchangeFragmentState.ShowCount(value)
    }

    private fun successCreate() {
        state.value = CurrencyExchangeFragmentState.SuccessCreate
    }

    fun convertFirstValue(amount: Double, from: String, to: String) {
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
            if (exchangeCountUseCase.invoke()<90){
                val currency = ExtendedCurrency.getAllCurrencies()
                for(cur in currency ){
                    if(cur.code.equals("EUR")){
                        balanceLocalUseCase.invoke(Balance(0,cur.code,cur.symbol,1000.00,1000.00,SomeUtils.convertDateTime()))
                    }
                    else{
                        balanceLocalUseCase.invoke(Balance(0,cur.code,cur.symbol,0.00,0.00,SomeUtils.convertDateTime()))
                    }

                }
            }


        }
    }

    fun exchange(fromAmount: Double,toAmount: Double,  fromCurrency: String, toCurrency: String){
        viewModelScope.launch {
            val size= exchangeCountUseCase.invoke()
            if (size>5){
                val item = size.toDouble() / 5
                if (SomeUtils.isInteger(item)) {
                    exchangeLocalUseCase.invoke(Exchange(0,fromCurrency,toCurrency,fromAmount,toAmount,0.00, SomeUtils.convertDateTime()))
                }
                else{
                    exchangeLocalUseCase.invoke(Exchange(0,fromCurrency,toCurrency,fromAmount,toAmount,0.70, SomeUtils.convertDateTime()))

                }
            }
            else{
                exchangeLocalUseCase.invoke(Exchange(0,fromCurrency,toCurrency,fromAmount,toAmount,0.00, SomeUtils.convertDateTime()))

            }
           val from =currencyDetailsUseCase.invoke(fromCurrency)
           val to =currencyDetailsUseCase.invoke(toCurrency)
            euroRate(toCurrency,fromAmount,toAmount)
            val fromAvailable=from
           // updateBalanceUseCase.invoke()
        }
    }
    fun euroRate(currencyName: String,fromAmount:Double,toAmount: Double) {
        viewModelScope.launch {
            convertUseCase.invoke(toAmount, currencyName, "EUR")
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
                        is BaseResult.Success ->  _euroRate.value = result.data.result
                        is BaseResult.Error -> showToast(result.rawResponse.toString())

                    }


                    updateFromBalance(currencyName,fromAmount,df.format(euroRate).toDouble())
                }
        }
    }
    fun updateFromBalance(from:String,fromAmount: Double,euroAmount: Double) {
        viewModelScope.launch {
            currencyDetailsUseCase.invoke(from)
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.stackTraceToString())
                }
                .collect { result ->
                    val fromAvailable=result.available!! - fromAmount
                    updateBalanceUseCase.invoke(fromAvailable,euroAmount,from)

                }
        }
    }
    fun countTransactions(){
        viewModelScope.launch {
            _count.value= exchangeCountUseCase.invoke()
            showCount(count.value)
            Log.e("_count",""+exchangeCountUseCase.invoke())

        }
    }
}

sealed class CurrencyExchangeFragmentState {
    object Init : CurrencyExchangeFragmentState()
    object SuccessCreate : CurrencyExchangeFragmentState()
    data class IsLoading(val isLoading: Boolean) : CurrencyExchangeFragmentState()
    data class ShowToast(val message: String) : CurrencyExchangeFragmentState()
    data class ShowCount(val count: Int?) : CurrencyExchangeFragmentState()
}