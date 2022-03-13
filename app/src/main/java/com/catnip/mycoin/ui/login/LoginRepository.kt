package com.catnip.mycoin.ui.login

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.local.preference.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class LoginRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepositoryImpl(), LoginContract.Repository {

    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<User, String> {
        val response = authApiDataSource.postLoginUser(loginRequest)
        if (response.isSuccess) {
            localDataSource.setAuthToken(response.data.token)
            val userDataResponse = authApiDataSource.getProfileData()
            localDataSource.saveUserData(userDataResponse.data)
        }
        return response
    }
}