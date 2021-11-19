package com.catnip.mycoin.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.Resource
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
class LoginViewModel @Inject constructor(private val repository: LoginRepository) :
    ViewModel(), LoginContract.ViewModel {

    private val syncUserLiveData = MutableLiveData<Resource<UserData>>()

    override fun getLoginResultLiveData(): LiveData<Resource<UserData>> = syncUserLiveData

    override fun saveSession(authToken: String) {
        repository.saveSession(authToken)
    }

    override fun loginUser(loginRequest: AuthRequest) {
        syncUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.postLoginUser(loginRequest)
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Success(response.data)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    syncUserLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }

}