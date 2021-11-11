package com.catnip.mycoin.data.local.datasource

import com.catnip.mycoin.data.local.SessionPreference
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
}