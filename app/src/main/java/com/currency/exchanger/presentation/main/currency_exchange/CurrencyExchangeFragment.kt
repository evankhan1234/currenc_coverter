package com.currency.exchanger.presentation.main.currency_exchange

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.currency.exchanger.R
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.databinding.FragmentCurrencyExchangeBinding
import com.currency.exchanger.domain.rate.Entity.RateEntity
import com.currency.exchanger.infra.utils.SomeUtils
import com.currency.exchanger.presentation.common.extension.showToast
import com.mynameismidori.currencypicker.CurrencyPicker
import com.mynameismidori.currencypicker.ExtendedCurrency
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat

@AndroidEntryPoint
class CurrencyExchangeFragment : Fragment(R.layout.fragment_currency_exchange){
    private var _binding : FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!
    private val df = DecimalFormat("00.00")
    private val viewModel : CurrencyExchangeViewModel by viewModels()
    private var firstRate : Double = 0.0
    private var lastRate : Double = 0.0
    private var firstCurrencyName : String = ""
    private var secondCurrencyName : String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCurrencyExchangeBinding.bind(view)
        observe()
        createProduct()
        observeFirstRate()
        observeSecondRate()
        main()
        count()

        binding.txtFirstAmount.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {

                if (s.toString()!=""){
                    val total : Double = s.toString().toDouble() *lastRate
                    binding.txtSecondUserAmount.setText(df.format(total))
                    Log.e("flag_spinner", "onSelectCurrency: $s")
                }
                else{
                    binding.txtSecondUserAmount.setText("")
                }

            }
        })
        binding.linearUser.setOnClickListener {
            binding.txtFirstAmount.requestFocus()
            binding.txtFirstAmount.setSelection(
                binding.txtFirstAmount.text.length
            )
            keyBoardOpen(binding.txtFirstAmount)
        }
    }
    private fun keyBoardOpen(view: View) {
        val imm = requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
    private fun setResultOkToPreviousFragment(){
        val r = Bundle().apply {
            putBoolean("success_create", true)
        }
        setFragmentResult("success_create", r)
    }

    private fun observe(){
        viewModel.mState.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { state -> handleState(state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: CurrencyExchangeFragmentState){
        when(state){
            is CurrencyExchangeFragmentState.IsLoading -> handleLoading(state.isLoading)
            is CurrencyExchangeFragmentState.IsExchange -> handleExchange(state.isLoading)
            is CurrencyExchangeFragmentState.SuccessCreate -> {
                setResultOkToPreviousFragment()
                findNavController().navigateUp()
            }
            is CurrencyExchangeFragmentState.ShowToast -> requireActivity().showToast(state.message)
            is CurrencyExchangeFragmentState.ShowCount ->  conventionFee(state.count)
            is CurrencyExchangeFragmentState.UpdateBalance ->  update(state.currencyName,state.amount,state.euroAmount,state.type)
            is CurrencyExchangeFragmentState.UpdateAmount ->  updateAmount(state.balance,state.amount,state.euroAmount,state.currencyName,state.type)
            is CurrencyExchangeFragmentState.Init -> Unit
        }
    }
    private fun observeFirstRate(){
        viewModel.firstRate.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { product ->
                product?.let { handleFirstRate(it) }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun observeSecondRate(){
        viewModel.secondRate.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { product ->
                product?.let { handleSecondRate(it) }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun count(){
        viewModel.count.flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
            .onEach { size ->
                size?.let { conventionFee(it) }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
    private fun handleFirstRate(rateEntity: RateEntity){
        val currency = ExtendedCurrency.getCurrencyByISO(rateEntity.query.from)
        firstRate=rateEntity.info.rate
        binding.txtCurrencySpinner.text = currency.name
        binding.imgFirstDynamic.setImageResource(currency.flag)
        binding.textSelectedCurrency.text = currency.symbol
    }
    private fun handleSecondRate(rateEntity: RateEntity){
        Log.e("rateEntity",""+rateEntity.query.to)
        val currency = ExtendedCurrency.getCurrencyByISO(rateEntity.query.to)
        lastRate=rateEntity.info.rate
        binding.txtSecondCurrencyText.text = currency.name
        binding.imgSecondDynamic.setImageResource(currency.flag)
        binding.textOtherSelectedCurrency.text=currency.symbol
    }
    private fun update(currencyName: String, amount: Double, euroAmount: Double, type: String){
        viewModel.update(currencyName, amount, euroAmount, type)
    }
    private fun updateAmount(balance: Balance, amount: Double, euroAmount: Double, currencyName: String, type: String){
        viewModel.updateData(balance, amount, euroAmount, currencyName, type)
    }
    private fun conventionFee(count: Int?){
        if (count!!>5){
            val item = count.toDouble() / 5
            if (SomeUtils.isInteger(item)) {
                binding.txtConventionsFee.setText("0.00 EUR")
            }
            else{
                binding.txtConventionsFee.setText("0.70 EUR")
            }
        }
        else{
            binding.txtConventionsFee.setText("0.00 EUR")
        }
    }
    private fun createProduct(){
        binding.firstSpinner.setOnClickListener {
            val picker: CurrencyPicker = CurrencyPicker.newInstance("Select Currency")
            picker.setListener { name, code, symbol, flagDrawableResID ->
                binding.txtCurrencySpinner.text = name
                firstCurrencyName=code
                binding.imgFirstDynamic.setImageResource(flagDrawableResID)
                if (code.equals("INR")) {
                    binding.textSelectedCurrency.text = "₹"
                } else {
                    binding.textSelectedCurrency.text = symbol
                }
                viewModel.convertFirstValue(1.00,code,secondCurrencyName)
                binding.txtSecondUserAmount.setText("")
                binding.txtFirstAmount.setText("")
                picker.dismiss()
            }
            picker.show(parentFragmentManager, "CURRENCY_PICKER")
        }
        binding.imgSecondCurrency.setOnClickListener {
            val picker: CurrencyPicker = CurrencyPicker.newInstance("Select Currency")
            picker.setListener { name, code, symbol, flagDrawableResID ->
                binding.txtSecondCurrencyText.text = name
                secondCurrencyName=code
                binding.imgSecondDynamic.setImageResource(flagDrawableResID)
                if (code.equals("INR")) {
                    binding.textOtherSelectedCurrency.text = "₹"
                } else {
                    binding.textOtherSelectedCurrency.text=symbol
                }
                viewModel.convertSecondValue(1.00,firstCurrencyName,secondCurrencyName)
                binding.txtSecondUserAmount.setText("")
                binding.txtFirstAmount.setText("")
                picker.dismiss()
            }
            picker.show(parentFragmentManager, "CURRENCY_PICKER")
        }
        binding.btnSend.setOnClickListener {
            if (binding.txtFirstAmount.text.toString().isEmpty()){
                Toast.makeText(requireActivity(),"Please enter amount",Toast.LENGTH_SHORT).show()
                viewModel.countTransactions()
                Log.e("flag_spinner", "onSelectCurrency: ${viewModel.count.value}")
            }
            else{
                val firstAmount=df.format(binding.txtFirstAmount.text.toString().toDouble())
                val secondAmount=df.format(binding.txtSecondUserAmount.text.toString().toDouble())
                viewModel.exchange(firstAmount.toDouble(),secondAmount.toDouble(),firstCurrencyName,secondCurrencyName)
                viewModel.countTransactions()
                binding.txtSecondUserAmount.setText("")
                binding.txtFirstAmount.setText("")
            }
        }
    }


    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun handleExchange(isLoading: Boolean) {
        binding.btnSend.isClickable = isLoading

    }
    private fun setProductNameError(e: String?){
      //  binding.productNameInput.error = e
    }

    private fun setProductPriceError(e: String?){
     //   binding.productPriceInput.error = e
    }


    private fun main(){
        viewModel.convertFirstValue(10.00,"EUR","USD")
        viewModel.convertSecondValue(10.00,"EUR","USD")
        firstCurrencyName="EUR"
        secondCurrencyName="USD"
        viewModel.createBalance()
        viewModel.countTransactions()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}