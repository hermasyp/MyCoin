package com.catnip.mycoin.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.Resource
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
class SplashScreenViewModel @Inject constructor(private val repository: SplashScreenRepository) :
    ViewModel(), SplashScreenContract.ViewModel {

    private val syncUserLiveData = MutableLiveData<Resource<UserData>>()

    override fun getSyncUserLiveData(): LiveData<Resource<UserData>> = syncUserLiveData

    override fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    override fun clearSession() {
        repository.clearSession()
    }

    override fun getSyncUser() {
        syncUserLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getSyncUser()
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