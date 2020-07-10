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

    val navigateToSelectedProperty = MutableLiveData<CurrencyModel>()
    private var formatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd")
        .withZone(ZoneId.systemDefault())

    val listOfExchangeRates = MutableLiveData<MutableList<CurrencyModel>>()

    val dateString = MutableLiveData<String>()

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    init {
        listOfExchangeRates.value = mutableListOf<CurrencyModel>()
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
                getRateModelsList(propertyResult.rates.toString())
                listOfExchangeRates.notifyObserver()
            } catch (e: Exception) {
                Log.d(TAG, "getFixerProperty: " + e.message)
            }
        }
    }

    fun getRateModelsList(rates:String){
        val ratePairs = rates.split(",")
        for (pair in ratePairs) {
            val splitedPairs = pair.split("=")
            listOfExchangeRates.value?.add(CurrencyModel(splitedPairs[0], splitedPairs[1]))
        }
    }

    fun displayCurrencyView(currencyModel: CurrencyModel) {
        navigateToSelectedProperty.value = currencyModel
    }
    fun displayCurrencyViewComplete() {
        navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}

