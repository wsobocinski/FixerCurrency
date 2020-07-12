package com.example.fixercurrency.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixercurrency.model.Currency

class CurrencyFragmentViewModel(currency: Currency):ViewModel() {
    val currentCurrency = MutableLiveData<Currency>()

    init {
        currentCurrency.value = currency
    }
}