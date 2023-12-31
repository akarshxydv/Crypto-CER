package com.example.cryptocer.data.CoinDeatails

import com.example.cryptocer.data.remote.dto.coinDeatails.Coin

data class CoinDetailDto(
    val crypto: HashMap<String, Coin>,
    val fiat: HashMap<String, String>,
    val success: Boolean
)

