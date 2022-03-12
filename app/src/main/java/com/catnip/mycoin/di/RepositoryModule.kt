package com.catnip.mycoin.di

import com.catnip.mycoin.data.local.preference.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.ui.coindetail.CoinDetailRepository
import com.catnip.mycoin.ui.coinlist.CoinListRepository
import com.catnip.mycoin.ui.splashscreen.SplashScreenRepository
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
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinListRepository(
        coinGeckoDataSource: CoinGeckoDataSource,
        localDataSource: LocalDataSource
    ): CoinListRepository {
        return CoinListRepository(coinGeckoDataSource, localDataSource)
    }

    @Singleton
    @Provides
    fun provideCoinDetailRepository(coinGeckoDataSource: CoinGeckoDataSource): CoinDetailRepository {
        return CoinDetailRepository(coinGeckoDataSource)
    }

    @Singleton
    @Provides
    fun provideSplashScreenRepository(
        authApiDataSource: AuthApiDataSource,
        localDataSource: LocalDataSource
    ): SplashScreenRepository {
        return SplashScreenRepository(authApiDataSource, localDataSource)
    }


}