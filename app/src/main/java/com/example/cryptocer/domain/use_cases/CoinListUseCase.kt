package com.example.cryptocer.domain.use_cases


import com.example.cryptocer.data.remote.dto.coinDeatails.CoinDetailDto
import com.example.cryptocer.domain.repository.CoinRepo
import com.example.cryptocer.util.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CoinListUseCase @Inject constructor(
    private val coinRepo: CoinRepo
) {
    operator fun invoke(apiKey:String): Flow<ResponseState<CoinDetailDto>> = flow {
        try {
            emit(ResponseState.Loading<CoinDetailDto>())
            val coinss = coinRepo.getAllCoin(apiKey)
            emit(ResponseState.Success<CoinDetailDto>(coinss))
        } catch (e: HttpException) {
            emit(ResponseState.Error<CoinDetailDto>(e.localizedMessage ?: "An Unexpected Error"))
        } catch (e: IOException) {
            emit(ResponseState.Error<CoinDetailDto>("Internet Error"))
        }
    }
}

