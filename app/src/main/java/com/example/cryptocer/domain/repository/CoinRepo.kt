package com.example.cryptocer.domain.repository

import com.example.cryptocer.data.CoinDeatails.CoinDetailDto
import com.example.cryptocer.data.remote.dto.coinRates.CoinRateDto

interface CoinRepo {
    suspend fun getAllCoin(apiKey:String):CoinDetailDto
    suspend fun getCoinRate(apiKey: String):CoinRateDto
}