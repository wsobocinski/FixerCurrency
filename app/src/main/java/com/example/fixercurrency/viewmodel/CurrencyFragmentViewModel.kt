package com.example.fixercurrency.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixercurrency.model.Currency

class CurrencyFragmentViewModel(currency: Currency):ViewModel() {
    val currentCurrency = MutableLiveData<Currency>()
    val exChangeRate = MutableLiveData<String>()

    init {
        currentCurrency.value = currency
        exChangeRate.value = ""
    }
    fun countExchangeValue(amount:String) {
        var result = String()
        if (amount == "") {
            return
        }
                result = String.format(
                    "%.2f",
                    amount.toDouble() * currentCurrency.value?.exchangeRate!!.toDouble()
                )
        exChangeRate.value = result + " ${currentCurrency.value?.symbol}"
    }
}