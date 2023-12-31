package com.example.cryptocer.domain.use_cases


import com.example.cryptocer.data.remote.dto.coinRates.CoinRateDto
import com.example.cryptocer.domain.repository.CoinRepo
import com.example.cryptocer.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinRatesUseCase @Inject constructor(
    private val coinRepo: CoinRepo
) {
    operator fun invoke(apiKey: String): Flow<ResponseState<CoinRateDto>> = flow {
        try {
            emit(ResponseState.Loading<CoinRateDto>())
            val coinsRatess = coinRepo.getCoinRate(apiKey)
            emit(ResponseState.Success<CoinRateDto>(coinsRatess))
        } catch (e: HttpException) {
            emit(ResponseState.Error<CoinRateDto>(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error<CoinRateDto>("Internet Error"))
        }
    }
}