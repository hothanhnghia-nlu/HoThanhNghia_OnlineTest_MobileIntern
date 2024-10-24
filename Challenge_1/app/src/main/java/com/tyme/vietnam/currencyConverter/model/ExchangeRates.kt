package com.tyme.vietnam.currencyConverter.model

data class ExchangeRates (
    val success: Boolean,
    val timestamp: Long,
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)