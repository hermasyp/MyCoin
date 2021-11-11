package com.catnip.mycoin.data.network.datasource.coin

import com.catnip.mycoin.data.network.model.response.coin.Coin
import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.data.network.services.CoinGeckoApiServices
import javax.inject.Inject


/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinGeckoDataSourceImpl @Inject constructor(private val coinGeckoApiServices: CoinGeckoApiServices) : CoinGeckoDataSource {
    override suspend fun getCoinList(): List<Coin> {
        return coinGeckoApiServices.getCoinList()
    }

    override suspend fun getCoinDetail(coinID: String): CoinDetailResponse {
        return coinGeckoApiServices.getCoinList(coinID)
    }

}