package com.example.fixercurrency.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixercurrency.model.Currency
import com.example.fixercurrency.model.FixerResponse
import com.example.fixercurrency.repository.FixerRepository
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class MainFragmentViewModel: ViewModel() {
    private val TAG = "MainFragmentViewModel"

    private val fixerRepository = FixerRepository
    val navigateToSelectedProperty = MutableLiveData<Currency>()
    var date = MutableLiveData<Instant>()
    private var dateForRequestFormatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("yyyy-MM-dd")
        .withZone(ZoneId.systemDefault())
    private var dateForTextFormatter: DateTimeFormatter = DateTimeFormatter
        .ofPattern("dd-MM-yyyy")
        .withZone(ZoneId.systemDefault())

    var currentFixerResponse = MutableLiveData<FixerResponse>()
    val listOfExchangeRates = MutableLiveData<MutableList<Currency>>()

    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }

    init {
        date.value = Instant.now()
        currentFixerResponse = fixerRepository.getFixerResponse(getFormattedRequestDate())
        listOfExchangeRates.value = mutableListOf<Currency>()
    }

    fun getUpdatedCurrencyList(){
        val newDayCurrencyList = createNewDayCurrencyList()
        newDayCurrencyList.add(0, (Currency("Dzień " + getFormattedTextDate(),
            "", getFormattedTextDate())))
        listOfExchangeRates.value?.addAll(newDayCurrencyList)
        listOfExchangeRates.notifyObserver()
    }


    private fun createNewDayCurrencyList(): MutableList<Currency> {
        val currencyList = mutableListOf<Currency>()
        val newDayCurrencyRates: String = currentFixerResponse.value?.rates.toString()
        if (newDayCurrencyRates != "null") {
            val splitedRates = newDayCurrencyRates.split(",")
            for (rate in splitedRates) {
                val symbolAndRate = rate.split("=")
                currencyList.add(Currency(
                    symbolAndRate[0],
                    symbolAndRate[1],
                    getFormattedTextDate()))
            }
        }
        return currencyList
    }

    fun getCurrencyListFromDayBefore() {
        date.value = date.value?.minus(1, ChronoUnit.DAYS)
        fixerRepository.getFixerResponse(getFormattedRequestDate())
        getUpdatedCurrencyList()
    }

    fun getFormattedRequestDate():String {
        return dateForRequestFormatter.format(date.value)
    }

    fun getFormattedTextDate():String {
        return dateForTextFormatter.format(date.value)
    }

    fun displayCurrencyView(currency: Currency) {
        navigateToSelectedProperty.value = currency
    }
    fun displayCurrencyViewComplete() {
        navigateToSelectedProperty.value = null
    }

    override fun onCleared() {
        super.onCleared()
        fixerRepository.job.cancel()
    }
}

