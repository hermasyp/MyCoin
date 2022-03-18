package com.catnip.mycoin.ui.register

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
interface RegisterContract {
    interface View : BaseContract.BaseView {
        fun setToolbar()
        fun setOnClick()
        fun checkFormValidation(): Boolean
        fun navigateToLogin()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getRegisterResponseLiveData(): LiveData<Resource<User>>
        fun registerUser(registerRequest: AuthRequest)
    }

    interface Repository : BaseContract.BaseRepository {
        suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<User, String>
    }
}