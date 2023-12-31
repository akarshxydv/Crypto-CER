package com.example.cryptocer.di

import com.example.cryptocer.data.remote.CoinLayerApi
import com.example.cryptocer.data.repository.CoinRepoImplement
import com.example.cryptocer.domain.repository.CoinRepo
import com.example.cryptocer.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CoinLayerModule {

    @Provides
    @Singleton
    fun provideCoinLayerApi():CoinLayerApi{
        return Retrofit.Builder()
            .baseUrl(Constant.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinLayerApi::class.java)
    }

    @Provides
    @Singleton
    fun coinLayerRepo(api:CoinLayerApi):CoinRepo{
        return CoinRepoImplement(api)
    }
}
