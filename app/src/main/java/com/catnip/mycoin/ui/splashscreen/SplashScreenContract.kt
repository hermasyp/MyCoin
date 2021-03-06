package com.catnip.mycoin.ui.splashscreen

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface SplashScreenContract {
    interface View {
        fun initView()
        fun checkLoginStatus()
        fun deleteSession()
        fun navigateToLogin()
        fun navigateToHome()
    }

    interface ViewModel {
        fun getSyncUserLiveData(): LiveData<Resource<User>>
        fun isUserLoggedIn(): Boolean
        fun clearSession()
        fun getSyncUser()
    }

    interface Repository {
        fun isUserLoggedIn(): Boolean
        fun clearSession()
        suspend fun getSyncUser(): BaseAuthResponse<User, String>
    }
}