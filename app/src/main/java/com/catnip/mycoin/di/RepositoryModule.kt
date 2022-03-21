package com.catnip.mycoin.di

import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.data.network.services.CoinGeckoApiServices
import com.catnip.mycoin.ui.coinlist.CoinListRepository
import com.catnip.mycoin.ui.login.LoginRepository
import com.catnip.mycoin.ui.register.RegisterRepository
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

    @Singleton
    @Provides
    fun provideSplashScreenRepository(
        authApiDataSource: AuthApiDataSource,
        localDataSource: LocalDataSource
    ) : SplashScreenRepository {
        return SplashScreenRepository(authApiDataSource, localDataSource)
    }


    @Singleton
    @Provides
    fun provideRegisterRepository(
        authApiDataSource: AuthApiDataSource,
    ) : RegisterRepository {
        return RegisterRepository(authApiDataSource)
    }
    @Singleton
    @Provides
    fun provideLoginRepository(
        authApiDataSource: AuthApiDataSource,
        localDataSource: LocalDataSource
    ) : LoginRepository {
        return LoginRepository(authApiDataSource,localDataSource)
    }
    @Singleton
    @Provides
    fun provideCoinListRepository(
        coinGeckoDataSource: CoinGeckoDataSource,
        localDataSource: LocalDataSource
    ) : CoinListRepository {
        return CoinListRepository(coinGeckoDataSource,localDataSource)
    }

}