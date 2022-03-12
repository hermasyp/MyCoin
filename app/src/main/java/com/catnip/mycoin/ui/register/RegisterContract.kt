package com.catnip.mycoin.ui.register

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.base.arch.BaseContract
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface RegisterContract {
    interface View : BaseContract.BaseView {
        fun initView()
        fun setToolbar()
        fun setOnClick()
        fun setLoadingState(isLoadingVisible: Boolean)
        fun checkFormValidation(): Boolean
        fun navigateToLogin()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getRegisterResponseLiveData(): LiveData<Resource<UserData>>
        fun registerUser(registerRequest: AuthRequest)
    }

    interface Repository : BaseContract.BaseRepository {
        suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}