package com.catnip.mycoin.ui.profile

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.local.preference.datasource.LocalDataSource
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

    override suspend fun saveCacheProfileData(response: BaseAuthResponse<User, String>) {
        if (response.isSuccess) {
            val modifiedUserData = localDataSource.getUserData()?.apply {
                id = response.data.id
                email = response.data.email
                username = response.data.username
                photo = response.data.photo
            }
            modifiedUserData?.let {
                localDataSource.saveUserData(it)
            }
        }
    }

    override suspend fun getProfileData(): BaseAuthResponse<User, String> {
        val response = authApiDataSource.getProfileData()
        saveCacheProfileData(response)
        return response
    }

    override suspend fun updateProfileData(
        email: String,
        username: String,
        photoProfile: File?
    ): BaseAuthResponse<User, String> {
        val response = authApiDataSource.updateProfileData(username, email, photoProfile)
        saveCacheProfileData(response)
        return response
    }

}