package com.example.fixercurrency.network

import com.example.fixercurrency.model.FixerResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val ACCESS_KEY = "32992604dd06160ef2fd25e339c693ff"

interface FixerService {
    @GET("latest")
    fun getLatestExchangesAsync(@Query("access_key") key:String = ACCESS_KEY):
            Deferred<FixerResponse>

    @GET("{date}")
    fun getHistoricalExchangeAsync(@Path("date") date:String,
                                   @Query("access_key") key: String = ACCESS_KEY):
            Deferred<FixerResponse>
}