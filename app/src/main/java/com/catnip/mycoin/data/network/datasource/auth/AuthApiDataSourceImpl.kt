package com.catnip.mycoin.data.network.datasource.auth

import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData
import com.catnip.mycoin.data.network.services.AuthApiService
import okhttp3.MultipartBody
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AuthApiDataSourceImpl
@Inject constructor(private val authApiService: AuthApiService) : AuthApiDataSource {
    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postLoginUser(loginRequest)
    }

    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String> {
        return authApiService.postRegisterUser(registerRequest)
    }

    override suspend fun getSyncUser(): BaseAuthResponse<UserData, String> {
        return authApiService.getSyncUser()
    }

    override suspend fun getUserData(): BaseAuthResponse<UserData, String> {
        return authApiService.getUserData()
    }

    override suspend fun putUserData(
        username: String,
        email: String
    ): BaseAuthResponse<UserData, String> {
        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("username", username)
            .build()
        return authApiService.putUserData(requestBody)
    }

}