package com.currency.exchanger.presentation.main.currency_exchange

import android.os.Handler
import android.os.Looper
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.domain.common.base.BaseResult
import com.currency.exchanger.domain.rate.Entity.RateEntity
import com.currency.exchanger.domain.rate.usecase.*
import com.currency.exchanger.infra.utils.SharedPrefs
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
    private val currencyDetailsUseCase: CurrencyDetailsUseCase,
    private var sharedPrefs: SharedPrefs

) : ViewModel() {
    private val df = DecimalFormat("00.00")
    private val state =
        MutableStateFlow<CurrencyExchangeFragmentState>(CurrencyExchangeFragmentState.Init)
    val mState: StateFlow<CurrencyExchangeFragmentState> get() = state

    private val _firstRate = MutableStateFlow<RateEntity?>(null)
    val firstRate: StateFlow<RateEntity?> get() = _firstRate

    private val _secondRate = MutableStateFlow<RateEntity?>(null)
    val secondRate: StateFlow<RateEntity?> get() = _secondRate


    private val _count = MutableStateFlow<Int?>(null)
    val count: StateFlow<Int?> get() = _count

    private val _fromBoolean = MutableStateFlow<Boolean?>(true)
    val fromBoolean: StateFlow<Boolean?> get() = _fromBoolean

    private val _toBoolean = MutableStateFlow<Boolean?>(true)
    val toBoolean: StateFlow<Boolean?> get() = _toBoolean

    private val _euroRate = MutableStateFlow<Double?>(null)
    val euroRate: StateFlow<Double?> get() = _euroRate
    private val _popUp = MutableStateFlow<Boolean?>(false)
    val popUp: StateFlow<Boolean?> get() = _popUp

    var message :String?=""
    private fun setLoading() {
        state.value = CurrencyExchangeFragmentState.IsLoading(true)
    }
    private fun hideLoading() {
        state.value = CurrencyExchangeFragmentState.IsLoading(false)
    }
    private fun exchangeHide(boolean: Boolean) {
        state.value = CurrencyExchangeFragmentState.IsExchange(boolean)
    }
    private fun showToast(message: String) {
        state.value = CurrencyExchangeFragmentState.ShowToast(message)
    }
    fun setValue(value: Boolean) {
        _popUp.value =false
    }
    private  fun updateBalance(currencyName: String, amount: Double, euroAmount: Double, type: String) {
        Log.e("currencyName","later"+currencyName)
        state.value = CurrencyExchangeFragmentState.UpdateBalance(currencyName,amount, euroAmount, type)

    }
    private  fun updateAmount(balance: Balance,amount: Double, euroAmount: Double,currencyName: String,type: String) {
        Log.e("getToBalance","updateAmount"+currencyName)
        state.value = CurrencyExchangeFragmentState.UpdateAmount(balance, amount, euroAmount, currencyName, type)

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
                        is BaseResult.Success -> _firstRate.value = result.data
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
                        is BaseResult.Success -> _secondRate.value = result.data
                        is BaseResult.Error -> showToast(result.rawResponse.toString())
                    }
                }
        }
    }

    fun createBalance() {

        viewModelScope.launch {
            if (!sharedPrefs.getCurrency()){
                if (exchangeCountUseCase.invoke()!=93) {
                    val currency = ExtendedCurrency.getAllCurrencies()
                    for (cur in currency) {
                        if (cur.code.equals("EUR")) {
                            balanceLocalUseCase.invoke(
                                Balance(
                                    0,
                                    cur.code,
                                    cur.symbol,
                                    1000.00,
                                    1000.00,
                                    SomeUtils.convertDateTime()
                                )
                            )
                        } else {
                            balanceLocalUseCase.invoke(
                                Balance(
                                    0,
                                    cur.code,
                                    cur.symbol,
                                    0.00,
                                    0.00,
                                    SomeUtils.convertDateTime()
                                )
                            )
                        }

                    }
                    sharedPrefs.saveCurrency(true)
                }
            }

        }
    }

    fun exchange(fromAmount: Double, toAmount: Double, fromCurrency: String, toCurrency: String) {
        _fromBoolean.value=true
        _toBoolean.value=true
        exchangeHide(false)
        viewModelScope.launch {
            val data =currencyDetailsUseCase.invoke(fromCurrency)
            val fromAvailable = data.available!! - fromAmount
            if (fromAvailable>-1){
                val size = exchangeCountUseCase.invoke()
                if (size > 5) {
                    val item = size.toDouble() / 5
                    if (SomeUtils.isInteger(item)) {
                        exchangeLocalUseCase.invoke(
                            Exchange(
                                0,
                                fromCurrency,
                                toCurrency,
                                fromAmount,
                                toAmount,
                                0.00,
                                SomeUtils.convertDateTime()
                            )
                        )
                        message="You have converted ${df.format(fromAmount)}  $fromCurrency to ${df.format(toAmount)} $toCurrency Commission Fee - 0.00 EUR"

                    } else {
                        exchangeLocalUseCase.invoke(
                            Exchange(
                                0,
                                fromCurrency,
                                toCurrency,
                                fromAmount,
                                toAmount,
                                0.70,
                                SomeUtils.convertDateTime()
                            )
                        )
                        message="You have converted ${df.format(fromAmount)}  $fromCurrency to ${df.format(toAmount)} $toCurrency Commission Fee - 0.70 EUR"

                    }
                } else
                {
                    exchangeLocalUseCase.invoke(
                        Exchange(
                            0,
                            fromCurrency,
                            toCurrency,
                            fromAmount,
                            toAmount,
                            0.00,
                            SomeUtils.convertDateTime()
                        )
                    )
                    message="You have converted ${df.format(fromAmount)}  $fromCurrency to ${df.format(toAmount)} $toCurrency Commission Fee - 0.00 EUR"

                }
            }
            euroRate(toCurrency, toAmount, "to")
            euroRate(fromCurrency, fromAmount, "from")

        }
    }

    private fun euroRate(currencyName: String, amount: Double, type: String) {
        viewModelScope.launch {
            convertUseCase.invoke(amount, currencyName, "EUR")
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
                        is BaseResult.Success ->
                            update(
                            currencyName,
                            amount,
                            result.data.info.rate,
                            type
                        )
                        is BaseResult.Error -> showToast(result.rawResponse.toString())

                    }

                }
        }
    }

    private fun getToBalance(currencyName: String, toAmount: Double, euroAmount: Double) {
        viewModelScope.launch {
            val data =currencyDetailsUseCase.invoke( currencyName)
            updateData(data, toAmount, euroAmount, currencyName,"to")

        }
    }

    private  fun getFromBalance(currencyName: String, fromAmount: Double, euroAmount: Double) {
        Log.e("getFromBalance :",currencyName+euroAmount)
        viewModelScope.launch {
            val data =currencyDetailsUseCase.invoke( currencyName)
            updateData(data, fromAmount, euroAmount, currencyName,"from")

        }
    }

    fun updateData(balance: Balance,amount: Double, euroAmount: Double,currencyName: String,type: String){

       viewModelScope.launch {
           if (type =="from"){
               if (fromBoolean.value!!){
                   val fromAvailable = balance.available!! - amount
                   val euro=euroAmount * fromAvailable
                   if (fromAvailable>-1){
                       if (balance.currencyName == "EUR"){
                           updateBalanceUseCase.invoke(fromAvailable, fromAvailable, currencyName)
                       }
                       else{
                           updateBalanceUseCase.invoke(fromAvailable, euro, currencyName)
                       }
                       _fromBoolean.value=false
                   }
                   else{
                       _popUp.value=false
                       _toBoolean.value=false
                       exchangeHide(true)
                   }
               }

           }
           else{
               if (toBoolean.value!!){
                   val toAvailable = balance.available!! + amount
                   Log.e("toAvailable","toAvailable"+toAvailable)
                   Log.e("toAvailable","currencyName"+balance.currencyName)
                   val euro=euroAmount * toAvailable
                   if (toAvailable>-1){
                       if (balance.currencyName == "EUR"){
                           updateBalanceUseCase.invoke(toAvailable, toAvailable, currencyName)
                       }
                       else{
                           updateBalanceUseCase.invoke(toAvailable, euro, currencyName)
                       }
                       exchangeHide(true)
                       _popUp.value=true
                       _toBoolean.value=false
                   }else{
                       _toBoolean.value=false
                       _popUp.value=true
                       exchangeHide(true)
                   }
               }
               else{
                   message="You have no balance"
                   _popUp.value=true
               }

           }

       }

    }

    fun countTransactions() {
        viewModelScope.launch {
            _count.value = exchangeCountUseCase.invoke()
            showCount(count.value)

        }
    }
    fun update(currencyName: String, amount: Double, euroAmount: Double, type: String){
        viewModelScope.launch {
            if (type == "to") {
                Handler(Looper.getMainLooper()).postDelayed({
                    getToBalance(currencyName, amount, euroAmount)
                }, 2000)

            } else {
                getFromBalance(currencyName, amount, euroAmount)
            }
        }
    }
}

sealed class CurrencyExchangeFragmentState {
    object Init : CurrencyExchangeFragmentState()
    object SuccessCreate : CurrencyExchangeFragmentState()
    data class IsLoading(val isLoading: Boolean) : CurrencyExchangeFragmentState()
    data class IsExchange(val isLoading: Boolean) : CurrencyExchangeFragmentState()
    data class ShowToast(val message: String) : CurrencyExchangeFragmentState()
    data class ShowCount(val count: Int?) : CurrencyExchangeFragmentState()
    data class UpdateBalance(val currencyName: String, val amount: Double,val euroAmount: Double, val type: String) : CurrencyExchangeFragmentState()
    data class UpdateAmount(val balance: Balance,val amount: Double,val  euroAmount: Double,val currencyName: String,val type: String) : CurrencyExchangeFragmentState()
}