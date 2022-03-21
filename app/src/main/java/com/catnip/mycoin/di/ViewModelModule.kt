package com.catnip.mycoin.di

import com.catnip.mycoin.base.arch.GenericViewModelFactory
import com.catnip.mycoin.ui.coinlist.CoinListRepository
import com.catnip.mycoin.ui.coinlist.CoinListViewModel
import com.catnip.mycoin.ui.login.LoginRepository
import com.catnip.mycoin.ui.login.LoginViewModel
import com.catnip.mycoin.ui.register.RegisterRepository
import com.catnip.mycoin.ui.register.RegisterViewModel
import com.catnip.mycoin.ui.splashscreen.SplashScreenRepository
import com.catnip.mycoin.ui.splashscreen.SplashScreenViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideSplashScreenViewModel(
        splashScreenRepository: SplashScreenRepository
    ): SplashScreenViewModel {
        return GenericViewModelFactory(SplashScreenViewModel(splashScreenRepository)).create(
            SplashScreenViewModel::class.java
        )
    }    
    @Provides
    @ActivityScoped
    fun provideRegisterViewModel(
        repository: RegisterRepository
    ): RegisterViewModel {
        return GenericViewModelFactory(RegisterViewModel(repository)).create(
            RegisterViewModel::class.java
        )
    }    
    @Provides
    @ActivityScoped
    fun provideLoginViewModel(
        repository: LoginRepository
    ): LoginViewModel {
        return GenericViewModelFactory(LoginViewModel(repository)).create(
            LoginViewModel::class.java
        )
    }    
    @Provides
    @ActivityScoped
    fun provideCoinListViewModel(
        repository: CoinListRepository
    ): CoinListViewModel {
        return GenericViewModelFactory(CoinListViewModel(repository)).create(
            CoinListViewModel::class.java
        )
    }

}