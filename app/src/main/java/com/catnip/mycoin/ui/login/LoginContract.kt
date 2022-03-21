package com.catnip.mycoin.ui.login

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.arch.BaseContract
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

interface LoginContract {
    interface View : BaseContract.BaseView {
        fun setToolbar()
        fun setOnClick()
        fun checkFormValidation(): Boolean
        fun navigateToHome()
        fun navigateToRegister()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getLoginResultLiveData() : LiveData<Resource<User>>
        fun loginUser(loginRequest: AuthRequest)
    }

    interface Repository : BaseContract.BaseRepository {
        suspend fun postLoginUser(loginRequest : AuthRequest) : BaseAuthResponse<User,String>
    }
}