package com.catnip.mycoin.ui.coindetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.base.arch.BaseViewModelImpl
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val repository: CoinDetailRepository) :
    BaseViewModelImpl(), CoinDetailContract.ViewModel {

    private var coinDetailLiveData = MutableLiveData<Resource<CoinDetailResponse>>()
    private var coinId = MutableLiveData<String?>()

    override fun getCoinDetailResponse(): LiveData<Resource<CoinDetailResponse>> =
        coinDetailLiveData

    override fun getCoinId(): LiveData<String?> = coinId

    override fun setIntentData(extras: Bundle?) {
        coinId.value = extras?.getString(CoinDetailActivity.EXTRAS_COIN_ID)
    }

    override fun getCoinDetail(id: String) {
        coinDetailLiveData.value = Resource.Loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getCoinDetail(id)
                viewModelScope.launch(Dispatchers.Main) {
                    coinDetailLiveData.value = Resource.Success(response)
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    coinDetailLiveData.value = Resource.Error(e.message.orEmpty())
                }
            }
        }
    }
}