package com.currency.exchanger.presentation.main.balance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.domain.rate.usecase.GetBalanceListUseCase
import com.currency.exchanger.presentation.main.home.HomeMainFragmentState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BalanceViewModel @Inject constructor(private val getBalanceListUseCase: GetBalanceListUseCase) :
    ViewModel() {
    private val state = MutableStateFlow<BalanceFragmentState>(BalanceFragmentState.Init)
    val mState: StateFlow<BalanceFragmentState> get() = state

    private val _balance = MutableStateFlow<List<Balance>>(mutableListOf())

    val balance: StateFlow<List<Balance>> get() = _balance

    init {
        fetchAllMyBalance()
    }


    private fun setLoading() {
        state.value = BalanceFragmentState.IsLoading(true)
    }

    private fun hideLoading() {
        state.value = BalanceFragmentState.IsLoading(false)
    }

    private fun showToast(message: String) {
        state.value = BalanceFragmentState.ShowToast(message)
    }

    private fun fetchAllMyBalance() {
        viewModelScope.launch {
            getBalanceListUseCase.invoke()
                .onStart {
                    setLoading()
                }
                .catch { exception ->
                    hideLoading()
                    showToast(exception.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    _balance.value = result
                }
        }
    }

}

sealed class BalanceFragmentState {
    object Init : BalanceFragmentState()
    data class IsLoading(val isLoading: Boolean) : BalanceFragmentState()
    data class ShowToast(val message: String) : BalanceFragmentState()
}