package com.example.fixercurrency.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.fixercurrency.model.FixerResponse
import com.example.fixercurrency.network.FixerService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val TAG = "FixerRepository"
object FixerRepository {
    private val FIXER_BASE_URL = "http://data.fixer.io/api/"
    private val fixerService: FixerService
    private val fixer = MutableLiveData<FixerResponse>()
    val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main )

    init {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        fixerService = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(FIXER_BASE_URL)
            .build()
            .create(FixerService::class.java)
    }

    fun getFixerResponse(date:String):MutableLiveData<FixerResponse>{
        coroutineScope.launch {
            val getPropertyDeferred = fixerService.getHistoricalExchangeAsync(date)
            try {
                val propertyResult = getPropertyDeferred.await()
                fixer.value = propertyResult
//                fixer.notifyObserver()
            } catch (e: Exception) {
                Log.d(TAG, "getFixerResponse: " + e.message)
            }
        }
        return fixer
    }
    private fun <T> MutableLiveData<T>.notifyObserver() {
        this.value = this.value
    }
}