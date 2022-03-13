package com.catnip.mycoin.ui.splashscreen

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.local.preference.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class SplashScreenRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) :BaseRepositoryImpl(), SplashScreenContract.Repository {
    override fun isUserLoggedIn(): Boolean {
        return localDataSource.isUserLoggedIn()
    }

    override fun clearSession() {
        localDataSource.clearSession()
    }

    override suspend fun getSyncUser(): BaseAuthResponse<User, String> {
        return authApiDataSource.getSyncUser()
    }
}