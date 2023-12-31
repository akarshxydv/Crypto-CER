package com.example.cryptocer.data.remote

import com.example.cryptocer.data.remote.dto.coinDeatails.CoinDetailDto
import com.example.cryptocer.data.remote.dto.coinRates.CoinRateDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinLayerApi {

    @GET("list")
    suspend fun getAllCoin(@Query("access_key") apiKey: String): CoinDetailDto

    @GET("live")
    suspend fun getCoinRate(@Query("access_key") apiKey: String):CoinRateDto
}