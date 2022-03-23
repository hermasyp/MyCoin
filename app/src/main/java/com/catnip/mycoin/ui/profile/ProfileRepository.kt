package com.catnip.mycoin.ui.profile

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.auth.AuthApiDataSource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import java.io.File
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class ProfileRepository @Inject constructor(
    private val authApiDataSource: AuthApiDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepositoryImpl(), ProfileContract.Repository {

    override fun clearSession() {
        localDataSource.clearSession()
    }

    override suspend fun saveUserData(response: BaseAuthResponse<User, String>) {
        if (response.isSuccess) {
            localDataSource.saveUserData(response.data)
        }
    }

    override suspend fun getProfileData(): BaseAuthResponse<User, String> {
        val response = authApiDataSource.getProfileData()
        saveUserData(response)
        return response
    }

    override suspend fun updateProfileData(
        email: String,
        username: String,
        photoProfile: File?
    ): BaseAuthResponse<User, String> {
        val response = authApiDataSource.updateProfileData(username,email, photoProfile)
        saveUserData(response)
        return response
    }
}