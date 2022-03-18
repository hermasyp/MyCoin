package com.catnip.mycoin.ui.register

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class RegisterRepository @Inject constructor(private val dataSource: AuthApiDataSource) :
    BaseRepositoryImpl(), RegisterContract.Repository {

    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<User, String> {
        return dataSource.postRegisterUser(registerRequest)
    }
}