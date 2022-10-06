package com.currency.exchanger.presentation.main.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.currency.exchanger.data.currency.local.dto.Exchange
import com.currency.exchanger.databinding.LayoutTransactionsListBinding
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.text.DecimalFormat

class HistoryAdapter(private val products: MutableList<Exchange>, val context: Context) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    fun updateList(mProducts: List<Exchange>) {
        products.clear()
        products.addAll(mProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: LayoutTransactionsListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(exchange: Exchange) {
            val df = DecimalFormat("00.00")
            val fromCurrency = ExtendedCurrency.getCurrencyByISO(exchange.fromCurrency)
            val toCurrency = ExtendedCurrency.getCurrencyByISO(exchange.toCurrency)
            itemBinding.txtFromAmount.text = fromCurrency.symbol + " " + df.format(exchange.fromAmount)
            itemBinding.txtToAmount.text = toCurrency.symbol + " " + df.format(exchange.convertedAmount)
            itemBinding.txtFromCurrencyName.text = "From - " + exchange.fromCurrency
            itemBinding.txtToCurrencyName.text = "Converted - " + exchange.toCurrency
            itemBinding.imgFirstDynamic.setImageResource(fromCurrency.flag)
            itemBinding.imgSecondDynamic.setImageResource(toCurrency.flag)
            itemBinding.txtDate.text=exchange.releaseDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutTransactionsListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemCount() = products.size
}