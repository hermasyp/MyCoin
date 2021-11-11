package com.catnip.mycoin.data.network.datasource.auth

import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface AuthApiDataSource {

    suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String>

    suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String>

    suspend fun getSyncUser(): BaseAuthResponse<UserData, String>

    suspend fun getUserData(): BaseAuthResponse<UserData, String>

    suspend fun putUserData(username: String, email: String): BaseAuthResponse<UserData, String>
}