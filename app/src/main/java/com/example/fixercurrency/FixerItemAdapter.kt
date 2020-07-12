package com.example.fixercurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixercurrency.model.Currency
import kotlinx.android.synthetic.main.currency_item_view.view.*


class FixerItemAdapter(private val onClickListener: OnClickListener)
    : RecyclerView.Adapter<CurrencyItemViewHolder>() {
    var data =  mutableListOf<Currency>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        holder.bind(data[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(data[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.currency_item_view, parent, false)
        return CurrencyItemViewHolder(view)
    }

    override fun getItemCount() = data.size

    class OnClickListener(val clickListener: (currency: Currency) -> Unit) {
        fun onClick(currency: Currency) = clickListener(currency)
    }
}

class CurrencyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val symbol = itemView.symbol
    private val exchangeValue = itemView.exchange_value

    fun bind(fixerProperty: Currency) {
        symbol.setText(fixerProperty.symbol)
        exchangeValue.setText(fixerProperty.exchangeValue)
    }
}