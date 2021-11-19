package com.catnip.mycoin.ui.coindetail

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.catnip.mycoin.base.Resource
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CoinDetailContract {
    interface ViewModel {
        fun getCoinDetailResponse(): LiveData<Resource<CoinDetailResponse>>
        fun getCoinId(): LiveData<String?>
        fun setIntentData(extras : Bundle?)
        fun getCoinDetail(id: String)
    }

    interface View {
        fun showContent(isContentVisible: Boolean)
        fun showLoading(isLoading: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String?)
        fun setContentData(data: CoinDetailResponse)
        fun getIntentData()
    }

    interface Repository {
        suspend fun getCoinDetail(id: String): CoinDetailResponse
    }
}