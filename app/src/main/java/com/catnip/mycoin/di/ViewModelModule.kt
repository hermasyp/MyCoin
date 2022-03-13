package com.catnip.mycoin.di

import com.catnip.mycoin.ui.coindetail.CoinDetailRepository
import com.catnip.mycoin.ui.coindetail.CoinDetailViewModel
import com.catnip.mycoin.ui.coinlist.CoinListRepository
import com.catnip.mycoin.ui.coinlist.CoinListViewModel
import com.catnip.mycoin.ui.login.LoginRepository
import com.catnip.mycoin.ui.login.LoginViewModel
import com.catnip.mycoin.ui.profile.ProfileRepository
import com.catnip.mycoin.ui.profile.ProfileViewModel
import com.catnip.mycoin.ui.register.RegisterRepository
import com.catnip.mycoin.ui.register.RegisterViewModel
import com.catnip.mycoin.ui.splashscreen.SplashScreenRepository
import com.catnip.mycoin.ui.splashscreen.SplashScreenViewModel
import com.catnip.notepadplusplus.base.arch.GenericViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

@Module
@InstallIn(ActivityComponent::class)
object ViewModelModule {

    @Provides
    @ActivityScoped
    fun provideCoinDetailViewModel(
        coinDetailRepository: CoinDetailRepository
    ): CoinDetailViewModel {
        return GenericViewModelFactory(CoinDetailViewModel(coinDetailRepository)).create(
            CoinDetailViewModel::class.java
        )
    }
    @Provides
    @ActivityScoped
    fun provideCoinListViewModel(
        coinListRepository: CoinListRepository
    ): CoinListViewModel {
        return GenericViewModelFactory(CoinListViewModel(coinListRepository)).create(
            CoinListViewModel::class.java
        )
    }
    @Provides
    @ActivityScoped
    fun provideLoginViewModel(
        loginRepository: LoginRepository
    ): LoginViewModel {
        return GenericViewModelFactory(LoginViewModel(loginRepository)).create(
            LoginViewModel::class.java
        )
    }
    @Provides
    @ActivityScoped
    fun provideRegisterViewModel(
        registerRepository: RegisterRepository
    ): RegisterViewModel {
        return GenericViewModelFactory(RegisterViewModel(registerRepository)).create(
            RegisterViewModel::class.java
        )
    }
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
    fun provideProfileViewModel(
        profileRepository: ProfileRepository
    ): ProfileViewModel {
        return GenericViewModelFactory(ProfileViewModel(profileRepository)).create(
            ProfileViewModel::class.java
        )
    }
}