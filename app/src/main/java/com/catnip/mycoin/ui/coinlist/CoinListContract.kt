package com.catnip.mycoin.ui.coinlist

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.response.coin.Coin

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CoinListContract {
    interface View {
        fun showLoading(isLoading: Boolean)
        fun showContent(isContentShown: Boolean)
        fun showErrMsg(isError: Boolean, msg: String?)
        fun initList()
        fun getData()
        fun initSwipeRefresh()
    }

    interface ViewModel {
        fun getCoinListLiveData(): LiveData<Resource<List<Coin>>>
        fun getCoinList()
        fun deleteSession()
    }

    interface Repository {
        suspend fun getCoinList(): List<Coin>
        fun deleteSession()
    }
}