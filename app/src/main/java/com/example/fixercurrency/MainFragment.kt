package com.example.fixercurrency

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.fixercurrency.databinding.FragmentMainBinding
import com.example.fixercurrency.web.FixerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainFragment : Fragment() {
    private val mainFragmentViewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewmodel = mainFragmentViewModel
        binding.previousDayValueButton.setOnClickListener{
            mainFragmentViewModel.getPreviousDay()
        }
        return binding.root
    }

}