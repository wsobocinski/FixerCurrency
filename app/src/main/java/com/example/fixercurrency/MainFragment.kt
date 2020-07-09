package com.example.fixercurrency

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fixercurrency.web.FixerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MainFragment"
class MainFragment : Fragment() {
    private var job = Job()
    private val coroutineScope = CoroutineScope(
        job + Dispatchers.Main )

    init {
        getFixerProperty("2019-11-11")
        getFixerProperty("2020-03-11")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
    private fun getFixerProperty(date:String) {
        coroutineScope.launch {
            val getPropertyDeferred = FixerApi.retrofitService.getHistoricalExchangeAsync(date)
            try {
                val propertyResult = getPropertyDeferred.await()
                Log.d(TAG, "onCreate: " + propertyResult.rates.PLN)
            } catch (e: Exception) {
                Log.d(TAG, "onCreate: " + e.message)
            }
        }
    }
    override fun onStop() {
        super.onStop()
        job.cancel()
    }
}