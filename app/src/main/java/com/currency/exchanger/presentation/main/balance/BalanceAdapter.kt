package com.currency.exchanger.presentation.main.balance

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackground
import androidx.recyclerview.widget.RecyclerView
import com.currency.exchanger.R
import com.currency.exchanger.data.rate.local.dto.Balance
import com.currency.exchanger.databinding.LayoutBalanceListBinding
import com.mynameismidori.currencypicker.ExtendedCurrency
import java.text.DecimalFormat

class BalanceAdapter(private val products: MutableList<Balance>, val context: Context) :
    RecyclerView.Adapter<BalanceAdapter.ViewHolder>() {

    fun updateList(mProducts: List<Balance>) {
        products.clear()
        products.addAll(mProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding: LayoutBalanceListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(balance: Balance) {
             val df = DecimalFormat("00.00")
            itemBinding.txtAvailableAmount.text = balance.currency_symbol + " " + df.format(balance.available)
            val currency = ExtendedCurrency.getCurrencyByISO(balance.currencyName)
            val sdk = android.os.Build.VERSION.SDK_INT
            itemBinding.imgFirstDynamic.setImageResource(currency.flag)
            if (balance.available!!>0){
                itemBinding.txtActive.text="flexible"
                itemBinding.txtActive.setBackgroundColor(R.color.teal_200)
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemBinding.txtActive.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_background_teal) )
                } else {
                    itemBinding.txtActive.background = ContextCompat.getDrawable(context, R.drawable.capsule_background_teal)
                }
            }
            else{
                itemBinding.txtActive.text="inflexible"
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    itemBinding.txtActive.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_background_danger) )
                } else {
                    itemBinding.txtActive.background = ContextCompat.getDrawable(context, R.drawable.capsule_background_danger)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutBalanceListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(products[position])

    override fun getItemCount() = products.size
}