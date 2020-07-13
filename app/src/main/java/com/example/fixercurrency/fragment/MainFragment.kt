package com.example.fixercurrency.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fixercurrency.FixerItemAdapter
import com.example.fixercurrency.viewmodel.MainFragmentViewModel
import com.example.fixercurrency.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val mainFragmentViewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewmodel = mainFragmentViewModel

        val recycleViewAdapter = FixerItemAdapter(FixerItemAdapter.OnClickListener {
                mainFragmentViewModel.displayCurrencyView(it)
            })

        binding.fixerPropertiesList.adapter = recycleViewAdapter

        mainFragmentViewModel.listOfExchangeRates.observe(viewLifecycleOwner, Observer {
                recycleViewAdapter.data = it
        })

        mainFragmentViewModel.currentFixerResponse.observe(viewLifecycleOwner, Observer {
            if (mainFragmentViewModel.listOfExchangeRates.value?.size == 0) {
                mainFragmentViewModel.getUpdatedCurrencyList()
            }
        })

        binding.fixerPropertiesList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE){
                    mainFragmentViewModel.getCurrencyListFromDayBefore()
                }
            }
        })

        mainFragmentViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( it != null && it.exchangeRate != "") {
                val action = MainFragmentDirections.actionMainFragmentToCurrencyFragment(it)
                findNavController(this).navigate(action)
                mainFragmentViewModel.displayCurrencyViewComplete()
            }
        })
        return binding.root
    }
}
