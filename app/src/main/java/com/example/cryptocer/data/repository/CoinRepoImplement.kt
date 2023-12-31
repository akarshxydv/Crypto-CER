package com.example.cryptocer.data.repository

import com.example.cryptocer.data.CoinDeatails.CoinDetailDto
import com.example.cryptocer.data.remote.CoinLayerApi
import com.example.cryptocer.data.remote.dto.coinRates.CoinRateDto
import com.example.cryptocer.domain.repository.CoinRepo
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CoinRepoImplement @Inject constructor(private val coinApi: CoinLayerApi):CoinRepo{
    override suspend fun getAllCoin(apiKey: String): CoinDetailDto {
        return coinApi.getAllCoin(apiKey)
    }

    override suspend fun getCoinRate(apiKey: String): CoinRateDto {
        return coinApi.getCoinRate(apiKey)
    }


}