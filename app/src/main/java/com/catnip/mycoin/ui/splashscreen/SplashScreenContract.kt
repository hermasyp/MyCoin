package com.catnip.mycoin.ui.splashscreen

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.arch.BaseContract
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface SplashScreenContract{
    interface View : BaseContract.BaseView {
        fun checkLoginStatus()
        fun deleteSession()
        fun navigateToLogin()
        fun navigateToHome()
    }

    interface ViewModel : BaseContract.BaseViewModel{
        fun getSyncUserLiveData(): LiveData<Resource<User>>
        fun getSyncUser()
        fun isUserLoggedIn(): Boolean
        fun clearSession()
    }

    interface Repository : BaseContract.BaseRepository{
        suspend fun getSyncUser(): BaseAuthResponse<User, String>
        fun isUserLoggedIn(): Boolean
        fun clearSession()
    }
}