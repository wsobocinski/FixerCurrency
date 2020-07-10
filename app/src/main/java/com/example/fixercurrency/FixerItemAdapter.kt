package com.example.fixercurrency

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.currency_item_view.view.*


class FixerItemAdapter: RecyclerView.Adapter<CurrencyItemViewHolder>() {
    var data =  mutableListOf<CurrencyModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.currency_item_view, parent, false)
        return CurrencyItemViewHolder(view)
    }

    override fun getItemCount() = data.size
}
class CurrencyItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val symbol = itemView.symbol
    val exchangeValue = itemView.exchange_value

    fun bind(fixerProperty: CurrencyModel) {
        symbol.setText(fixerProperty.symbol)
        exchangeValue.setText(fixerProperty.exchangeValue)
    }
}