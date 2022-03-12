package com.catnip.mycoin.data.local.preference.datasource

import com.catnip.mycoin.data.local.preference.SessionPreference
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class LocalDataSourceImpl
@Inject constructor(private val sessionPreference: SessionPreference) : LocalDataSource {
    override fun getAuthToken(): String? {
        return sessionPreference.authToken
    }

    override fun setAuthToken(authToken: String?) {
        sessionPreference.authToken = authToken
    }

    override fun isUserLoggedIn(): Boolean {
        return !sessionPreference.authToken.isNullOrEmpty()
    }

    override fun deleteSession() {
        sessionPreference.deleteSession()
    }
}