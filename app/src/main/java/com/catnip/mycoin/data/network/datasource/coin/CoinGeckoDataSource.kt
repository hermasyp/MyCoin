package com.catnip.mycoin.data.network.datasource.coin

import com.catnip.mycoin.data.network.model.response.coin.detail.CoinDetailResponse
import com.catnip.mycoin.data.network.model.response.coin.list.Coin

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface CoinGeckoDataSource {
    suspend fun getCoinList(): List<Coin>
    suspend fun getCoinDetail(coinID: String): CoinDetailResponse
}