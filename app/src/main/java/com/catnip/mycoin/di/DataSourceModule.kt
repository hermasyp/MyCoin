package com.catnip.mycoin.di

import com.catnip.mycoin.data.local.SessionPreference
import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.local.datasource.LocalDataSourceImpl
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSourceImpl
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSourceImpl
import com.catnip.mycoin.data.network.services.AuthApiServices
import com.catnip.mycoin.data.network.services.CoinGeckoApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @Singleton
    @Provides
    fun provideAuthDataSource(authApiService: AuthApiServices): AuthApiDataSource {
        return AuthApiDataSourceImpl(authApiService)
    }
    @Singleton
    @Provides
    fun provideCoinGeckoDataSource(coinGeckoApiServices: CoinGeckoApiServices): CoinGeckoDataSource {
        return CoinGeckoDataSourceImpl(coinGeckoApiServices)
    }
    @Singleton
    @Provides
    fun provideLocalDataSource(sessionPreference: SessionPreference): LocalDataSource {
        return LocalDataSourceImpl(sessionPreference)
    }
}