package com.example.fixercurrency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.fixercurrency.databinding.FragmentCurrencyBinding

class CurrencyFragment : Fragment() {
  val args: CurrencyFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentCurrencyBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        val currencyModel = args.selectedProperty
        binding.currencySymbol.setText(currencyModel.symbol)
        binding.currencyValue.setText(currencyModel.exchangeValue)
        return binding.root
    }
}