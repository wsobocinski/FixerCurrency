package com.example.fixercurrency


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fixercurrency.web.FixerApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private var job = Job()
    private val coroutineScope = CoroutineScope(
        job + Dispatchers.Main )

    init {
        getFixerProperty("2019-11-11")
        getFixerProperty("2020-03-11")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: ")

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