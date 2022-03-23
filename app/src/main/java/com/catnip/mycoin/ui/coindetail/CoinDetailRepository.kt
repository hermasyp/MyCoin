package com.catnip.mycoin.ui.coindetail

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinDetailRepository @Inject constructor(private val coinGeckoDataSource: CoinGeckoDataSource) :
    BaseRepositoryImpl(), CoinDetailContract.Repository {
    override suspend fun getCoinDetail(id: String): CoinDetailResponse {
        return coinGeckoDataSource.getCoinDetail(id)
    }
}