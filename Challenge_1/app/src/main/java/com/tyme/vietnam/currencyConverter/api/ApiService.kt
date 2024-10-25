package com.tyme.vietnam.currencyConverter.api

import com.tyme.vietnam.currencyConverter.model.ExchangeRates
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest")
    fun getExchangeRates(
        @Query("access_key") accessKey: String,
    ): Call<ExchangeRates>
}