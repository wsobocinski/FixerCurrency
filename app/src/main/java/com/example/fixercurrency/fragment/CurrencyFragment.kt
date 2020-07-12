package com.example.fixercurrency.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.fixercurrency.databinding.FragmentCurrencyBinding
import com.example.fixercurrency.model.Currency
import com.example.fixercurrency.viewmodel.CurrencyFragmentViewModel
import com.example.fixercurrency.viewmodel.CurrencyViewModelFactory

class CurrencyFragment : Fragment() {
  private val args: CurrencyFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentCurrencyBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        val currencyModel: Currency = args.selectedProperty
        val viewModelFactory = CurrencyViewModelFactory(currencyModel)
        binding.viewModel = ViewModelProvider(
            this, viewModelFactory).get(CurrencyFragmentViewModel::class.java)
        return binding.root
    }
}