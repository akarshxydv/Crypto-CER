package com.example.cryptocer.domain.model
import com.example.cryptocer.data.remote.dto.coinDeatails.Coin

data class CombinedCoinInfo(
    val coin: Coin,
    val rate: String
)


