package com.catnip.mycoin.data.local.datasource

import com.catnip.mycoin.data.network.model.response.auth.User

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface LocalDataSource {
    fun getAuthToken(): String?
    fun setAuthToken(authToken: String?)
    fun isUserLoggedIn(): Boolean
    fun saveUserData(user: User)
    fun getUserData(): User?
    fun clearSession()
}