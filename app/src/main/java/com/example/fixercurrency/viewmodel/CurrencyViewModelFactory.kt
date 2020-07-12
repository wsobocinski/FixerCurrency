package com.example.fixercurrency.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.fixercurrency.model.Currency

class CurrencyViewModelFactory(private val currency:Currency): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyFragmentViewModel::class.java)) {
            return CurrencyFragmentViewModel(currency) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}