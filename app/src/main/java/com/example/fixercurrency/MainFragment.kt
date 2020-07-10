package com.example.fixercurrency

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fixercurrency.databinding.FragmentMainBinding


class MainFragment : Fragment() {
    private val mainFragmentViewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewmodel = mainFragmentViewModel
        val adapter = FixerItemAdapter(FixerItemAdapter.OnClickListener {
            mainFragmentViewModel.displayCurrencyView(it)
        })
        binding.fixerPropertiesList.adapter = adapter
        mainFragmentViewModel.listOfExchangeRates.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })
        binding.fixerPropertiesList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE){
                    mainFragmentViewModel.getPreviousDay()
                }
            }
        })
        mainFragmentViewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                val action = MainFragmentDirections.actionMainFragmentToCurrencyFragment(it)
                findNavController(this).navigate(action)
                mainFragmentViewModel.displayCurrencyViewComplete()
            }
        })
        return binding.root
    }
}
