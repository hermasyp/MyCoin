package com.catnip.mycoin.ui.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.arch.BaseViewModelImpl
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) :
    BaseViewModelImpl(), ProfileContract.ViewModel {

    private val profileLiveData = MutableLiveData<Resource<User>>()
    private val changeProfileResultLiveData = MutableLiveData<Resource<User>>()


    override fun getProfileData() {
        profileLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getProfileData()
                viewModelScope.launch(Dispatchers.Main) {
                    profileLiveData.value = Resource.Success(response.data)
                }
            }catch (e : Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    profileLiveData.value = Resource.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    override fun updateProfileData(
        email: String,
        username: String,
        photoProfile: File?
    ){
        changeProfileResultLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.updateProfileData(email,username,photoProfile)
                viewModelScope.launch(Dispatchers.Main) {
                    changeProfileResultLiveData.value = Resource.Success(response.data)
                }
            }catch (e : Exception){
                viewModelScope.launch(Dispatchers.Main) {
                    changeProfileResultLiveData.value = Resource.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    override fun logout() {
        repository.clearSession()
    }

    override fun getChangeProfileResultLiveData(): MutableLiveData<Resource<User>> = changeProfileResultLiveData

    override fun getProfileLiveData(): MutableLiveData<Resource<User>> = profileLiveData

}