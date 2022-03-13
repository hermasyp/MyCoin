package com.catnip.mycoin.ui.coinlist

import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.base.arch.BaseContract
import com.catnip.mycoin.data.network.model.response.auth.User
import com.catnip.mycoin.data.network.model.response.coin.Coin

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CoinListContract {
    interface View : BaseContract.BaseView {
        fun initList()
        fun getData()
        fun initSwipeRefresh()
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getCoinListLiveData(): LiveData<Resource<List<Coin>>>
        fun getCoinList()
        fun deleteSession()
        fun getUserData() : User?
    }

    interface Repository : BaseContract.BaseRepository{
        suspend fun getCoinList(): List<Coin>
        fun deleteSession()
        fun getUserData() : User?
    }
}