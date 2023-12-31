package com.example.cryptocer.viewModel.coinList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocer.data.CoinDeatails.CoinDetailDto
import com.example.cryptocer.data.remote.dto.coinRates.CoinRateDto
import com.example.cryptocer.domain.model.CombinedCoinInfo
import com.example.cryptocer.domain.use_cases.CoinListUseCase
import com.example.cryptocer.domain.use_cases.CoinRatesUseCase
import com.example.cryptocer.util.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val coinListUseCase: CoinListUseCase,
                                            private val coinRatesUseCase:CoinRatesUseCase):ViewModel() {

    private val coinValue = MutableStateFlow(CoinState())
    var _coinValue : StateFlow<CoinState> = coinValue
    private var coinInfo:CoinDetailDto? = null
    private var coinRate:CoinRateDto?=null

    fun getAllCoin(apiKey:String)=viewModelScope.launch(Dispatchers.IO){
        coinListUseCase(apiKey = apiKey).collect {
            when(it){
                is ResponseState.Success ->{
                    //coinValue.value = CoinState(coinDetail = it.data)
                    coinInfo=it.data
                }
                is ResponseState.Loading ->{
                    coinValue.value = CoinState(isLoading = true)
                }
                is ResponseState.Error ->{
                    coinValue.value = CoinState(error = it.message?:"An Unexpected Error")
                }
            }
        }
        coinRatesUseCase(apiKey=apiKey).collect{
            when(it){
                is ResponseState.Success ->{
                    coinRate= it.data
                }
                is ResponseState.Loading ->{
                    coinValue.value = CoinState(isLoading = true)
                }
                is ResponseState.Error ->{
                    coinValue.value = CoinState(error = it.message?:"An Unexpected Error")
                }
            }
        }
        coinValue.value=CoinState(coinDetail = combineResponses(coinInfo,coinRate))
    }

    private fun combineResponses(
        coinDetailDto: CoinDetailDto?,
        coinRateDto: CoinRateDto?
    ): List<CombinedCoinInfo> {
        val combinedList = mutableListOf<CombinedCoinInfo>()

        if (coinDetailDto?.success == true && coinRateDto?.success == true) {
            val cryptoMap = coinDetailDto.crypto
            val ratesMap = coinRateDto.rates

            for ((symbol, coin) in cryptoMap) {
                val rate = ratesMap[symbol]
                val rateToSixD=sixDecimal(rate)
                if (rate != null) {
                    val combinedInfo = CombinedCoinInfo(coin, rateToSixD)
                    combinedList.add(combinedInfo)
                }
            }
        }

        return combinedList
    }
    private fun sixDecimal(rate:Double?):String {
        //val number = 123.4567890123456789

        // Format the number using String.format with up to six decimal places
        val formattedNumber = String.format("%.6f", rate)

        // Print the formatted number
        //println("Formatted Number: $formattedNumber")
        return formattedNumber
    }
}