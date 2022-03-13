package com.catnip.mycoin.ui.profile

import androidx.lifecycle.MutableLiveData
import com.catnip.mycoin.base.arch.BaseContract
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.BaseAuthResponse
import com.catnip.mycoin.data.network.model.response.auth.User
import java.io.File

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface ProfileContract {
    interface View : BaseContract.BaseView {
        fun setToolbar()
        fun setOnClickListeners()
        fun checkFormValidation(): Boolean
        fun setDataToView(data: User)
        fun getData()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getChangeProfileResultLiveData(): MutableLiveData<Resource<User>>
        fun getProfileLiveData(): MutableLiveData<Resource<User>>
        fun getProfileData()
        fun updateProfileData(email: String, username: String, photoProfile: File? = null)
        fun logout()
    }

    interface Repository : BaseContract.BaseRepository {
        fun clearSession()
        suspend fun saveCacheProfileData(response: BaseAuthResponse<User, String>)
        suspend fun getProfileData(): BaseAuthResponse<User, String>
        suspend fun updateProfileData(
            email: String,
            username: String,
            photoProfile: File? = null
        ): BaseAuthResponse<User, String>
    }
}