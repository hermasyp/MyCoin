package com.catnip.mycoin.ui.coinlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.arch.BaseViewModelImpl
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.network.model.response.auth.User
import com.catnip.mycoin.data.network.model.response.coin.list.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@HiltViewModel
class CoinListViewModel @Inject constructor(private val repository: CoinListRepository) :
    BaseViewModelImpl(), CoinListContract.ViewModel {

    private val coinListResponseLiveData = MutableLiveData<Resource<List<Coin>>>()

    override fun getCoinListLiveData(): LiveData<Resource<List<Coin>>> = coinListResponseLiveData

    override fun getCoinList() {
        coinListResponseLiveData.value = Resource.Loading()
        viewModelScope.launch {
            coinListResponseLiveData.value = repository.getCoinList()
        }
    }


    override fun getUserData(): User? {
        return repository.getUserData()
    }

}