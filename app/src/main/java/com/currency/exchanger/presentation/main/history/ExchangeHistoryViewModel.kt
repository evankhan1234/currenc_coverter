package com.currency.exchanger.presentation.main.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.domain.rate.usecase.GetExchangeListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExchangeHistoryViewModel @Inject constructor(private val getExchangeListUseCase: GetExchangeListUseCase) :
    ViewModel() {
    private val state = MutableStateFlow<ExchangeFragmentState>(ExchangeFragmentState.Init)
    val mState: StateFlow<ExchangeFragmentState> get() = state

    private val _exchange = MutableStateFlow<List<Exchange>>(mutableListOf())

    val exchange: StateFlow<List<Exchange>> get() = _exchange

    init {
        fetchAllMyExchange()
    }


    private fun setLoading() {
        state.value = ExchangeFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = ExchangeFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = ExchangeFragmentState.ShowToast(message)
    }

    private fun fetchAllMyExchange() {
        viewModelScope.launch {
            getExchangeListUseCase.invoke()
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    _exchange.value = result
                }
        }
    }

}

sealed class ExchangeFragmentState {
    object Init : ExchangeFragmentState()
    data class IsLoading(val isLoading: Boolean) : ExchangeFragmentState()
    data class ShowToast(val message: String) : ExchangeFragmentState()
}