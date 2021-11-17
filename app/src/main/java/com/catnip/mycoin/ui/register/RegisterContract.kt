package com.catnip.mycoin.ui.register

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.UserData

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface RegisterContract {
    interface View {
        fun initView()
        fun initViewModel()
    }

    interface ViewModel {
        fun getRegisterResponseLiveData() : LiveData<Resource<UserData>>
        fun registerUser(registerRequest: AuthRequest)
    }

    interface Repository {
        suspend fun postRegisterUser(registerRequest: AuthRequest): BaseAuthResponse<UserData, String>
    }
}