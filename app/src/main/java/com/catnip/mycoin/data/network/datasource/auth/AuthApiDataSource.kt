package com.catnip.mycoin.data.network.datasource.auth

import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import java.io.File

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

interface AuthApiDataSource {

    suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<User, String>

    suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<User, String>

    suspend fun getSyncUser(): BaseAuthResponse<User, String>

    suspend fun getProfileData(): BaseAuthResponse<User, String>

    suspend fun updateProfileData(
        username: String,
        email: String,
        profilePhoto: File? = null
    ): BaseAuthResponse<User, String>
}