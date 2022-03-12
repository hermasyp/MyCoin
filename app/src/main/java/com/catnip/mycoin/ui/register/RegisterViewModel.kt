package com.catnip.mycoin.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.base.arch.BaseViewModelImpl
import com.catnip.mycoin.data.network.model.request.auth.AuthRequest
import com.catnip.mycoin.data.network.model.response.auth.UserData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) :
    BaseViewModelImpl(), RegisterContract.ViewModel {
    private val registerUserLiveData = MutableLiveData<Resource<UserData>>()

    override fun getRegisterResponseLiveData(): LiveData<Resource<UserData>> = registerUserLiveData

    override fun registerUser(registerRequest: AuthRequest) {
        registerUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postRegisterUser(registerRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    registerUserLiveData.value = Resource.Success(response.data)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    registerUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}