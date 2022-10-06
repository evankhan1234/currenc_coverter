package com.currency.exchanger.presentation.main.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.exchanger.R
import com.currency.exchanger.data.rate.local.dto.Exchange
import com.currency.exchanger.databinding.FragmentExchangeHistoryBinding
import com.currency.exchanger.presentation.common.extension.showToast

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ExchangeHistoryFragment : Fragment(R.layout.fragment_exchange_history) {
    private var _binding: FragmentExchangeHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExchangeHistoryViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExchangeHistoryBinding.bind(view)
        setupRecyclerView()
        observe()
        observeBalance()

    }
    private fun observe() {
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun handleState(state: ExchangeFragmentState) {
        when (state) {
            is ExchangeFragmentState.IsLoading -> handleLoading(state.isLoading)
            is ExchangeFragmentState.ShowToast -> requireActivity().showToast(state.message)
            is ExchangeFragmentState.Init -> Unit
        }
    }

    private fun observeBalance() {
        viewModel.exchange
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { exchange ->
                handleProducts(exchange)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun handleProducts(exchanges: List<Exchange>) {
        binding.historyRecyclerView.adapter?.let {
            if (it is HistoryAdapter) {
                it.updateList(exchanges)
            }
        }
    }

    private fun setupRecyclerView() {
        val mAdapter = HistoryAdapter(mutableListOf(), requireContext())
        binding.historyRecyclerView.apply {
            adapter = mAdapter
            layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.VERTICAL, false
            )
        }
    }

    private fun handleLoading(isLoading: Boolean) {
        //  binding.saveButton.isEnabled = !isLoading
    }

    private fun setProductNameError(e: String?) {
        //  binding.productNameInput.error = e
    }

    private fun setProductPriceError(e: String?) {
        //   binding.productPriceInput.error = e
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}