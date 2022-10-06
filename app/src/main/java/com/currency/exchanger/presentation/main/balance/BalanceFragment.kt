package com.currency.exchanger.presentation.main.balance

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.currency.exchanger.R
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.databinding.FragmentBalanceBinding
import com.currency.exchanger.presentation.common.extension.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class BalanceFragment : Fragment(R.layout.fragment_balance) {
    private var _binding: FragmentBalanceBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BalanceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentBalanceBinding.bind(view)
        setupRecyclerView()
        observe()
        observeBalance()
    }

    private fun observe() {
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: BalanceFragmentState) {
        when (state) {
            is BalanceFragmentState.IsLoading -> handleLoading(state.isLoading)
            is BalanceFragmentState.ShowToast -> requireActivity().showToast(state.message)
            is BalanceFragmentState.Init -> Unit
        }
    }

    private fun observeBalance() {
        viewModel.balance
            .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { products ->
                handleProducts(products)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }


    private fun handleProducts(balances: List<Balance>) {
        binding.productsRecyclerView.adapter?.let {
            if (it is BalanceAdapter) {
                it.updateList(balances)
            }
        }
    }

    private fun setupRecyclerView() {
        val mAdapter = BalanceAdapter(mutableListOf(), requireContext())

        binding.productsRecyclerView.apply {
            adapter = mAdapter
            layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
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