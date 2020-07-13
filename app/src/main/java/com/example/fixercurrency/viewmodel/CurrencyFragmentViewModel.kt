package com.example.fixercurrency.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixercurrency.model.Currency

class CurrencyFragmentViewModel(currency: Currency) : ViewModel() {
    val currentCurrency = MutableLiveData<Currency>()
    val exchangeValue = MutableLiveData<String>()

    init {
        currentCurrency.value = currency
        exchangeValue.value = ""
    }

    fun countExchangeValue(baseCurrencyAmount: String) {
        if (baseCurrencyAmount == "") {
            return
        }
        val result = String.format(
            "%.2f",
            baseCurrencyAmount.toDouble() * currentCurrency.value?.exchangeRate!!.toDouble()
        )
        exchangeValue.value = result + " ${currentCurrency.value?.symbol}"
    }
}