package com.example.fixercurrency

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixercurrency.web.FixerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainFragmentViewModel: ViewModel() {
    private val TAG = "MainFragmentViewModel"
    private var job = Job()
    private val coroutineScope = CoroutineScope(
        job + Dispatchers.Main )
    var date = Instant.now()
    var formatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd")
        .withZone(ZoneId.systemDefault())

    val dateString:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val plnExchangeValue:MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    init {
        getFixerProperty(formatter.format(date))
    }

    fun getPreviousDay() {
        date = date.minus(1, ChronoUnit.DAYS)
        getFixerProperty(formatter.format(date))
    }


    private fun getFixerProperty(date:String) {
        dateString.value = date
        coroutineScope.launch {
            val getPropertyDeferred = FixerApi.retrofitService.getHistoricalExchangeAsync(date)
            try {
                val propertyResult = getPropertyDeferred.await()
                plnExchangeValue.value = propertyResult.rates.PLN.toString()
            } catch (e: Exception) {
                Log.d(TAG, "getFixerProperty: " + e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}