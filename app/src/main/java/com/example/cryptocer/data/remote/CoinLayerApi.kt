package com.example.cryptocer.data.remote

import com.example.cryptocer.data.CoinDeatails.CoinDetailDto
import com.example.cryptocer.data.remote.dto.coinRates.CoinRateDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinLayerApi {

    @GET("list")
    fun getAllCoin(@Query("access_key") apiKey: String): CoinDetailDto

    @GET("live")
    fun getCoinRate(@Query("access_key") apiKey: String):CoinRateDto
}