package com.example.cryptocer.data.remote.dto.coinRates

data class CoinRateDto(
    val privacy: String,
    val rates: HashMap<String, Double>,
    val success: Boolean,
    val target: String,
    val terms: String,
    val timestamp: Int
)

