package com.catnip.mycoin.di

import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.ui.coinlist.CoinListRepository
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
    fun provideCoinListRepository(coinGeckoDataSource: CoinGeckoDataSource): CoinListRepository {
        return CoinListRepository(coinGeckoDataSource)
    }
}