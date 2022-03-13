package com.catnip.mycoin.data.network.datasource.auth

import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import com.catnip.mycoin.data.network.services.AuthApiService
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class AuthApiDataSourceImpl
@Inject constructor(private val authApiService: AuthApiService) : AuthApiDataSource {
    override suspend fun postLoginUser(loginRequest: AuthRequest): BaseAuthResponse<User, String> {
        return authApiService.postLoginUser(loginRequest)
    }

    override suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<User, String> {
        return authApiService.postRegisterUser(registerRequest)
    }

    override suspend fun getSyncUser(): BaseAuthResponse<User, String> {
        return authApiService.getSyncUser()
    }

    override suspend fun getProfileData(): BaseAuthResponse<User, String> {
        return authApiService.getUserData()
    }

    override suspend fun updateProfileData(
        username: String,
        email: String,
        profilePhoto: File?
    ): BaseAuthResponse<User, String> {

        val requestBodyBuilder = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("email", email)
            .addFormDataPart("username", username)
        if (profilePhoto != null) {
            requestBodyBuilder.addFormDataPart(
                "photo", profilePhoto.name, RequestBody.create(
                    MediaType.parse("image/*"),
                    profilePhoto
                )
            )
        }
        return authApiService.putUserData(requestBodyBuilder.build())
    }

}