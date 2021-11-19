package com.catnip.mycoin.ui.splashscreen

import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class SplashScreenRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : SplashScreenContract.Repository {
    override fun isUserLoggedIn(): Boolean {
        return localDataSource.isUserLoggedIn()
    }

    override fun clearSession() {
        localDataSource.deleteSession()
    }

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return authApiDataSource.getSyncUser()
    }
}