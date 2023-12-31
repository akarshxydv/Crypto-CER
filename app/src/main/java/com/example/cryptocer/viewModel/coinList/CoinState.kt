package com.example.cryptocer.viewModel.coinList

import com.example.cryptocer.domain.model.CombinedCoinInfo

data class CoinState(
    val isLoading: Boolean = false,
    val coinDetail: List<CombinedCoinInfo> = emptyList(),
    val error: String = ""
)
