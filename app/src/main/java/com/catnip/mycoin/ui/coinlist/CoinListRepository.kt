package com.catnip.mycoin.ui.coinlist

import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.data.network.model.response.coin.Coin
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinListRepository
@Inject constructor(
    private val dataSource: CoinGeckoDataSource,
    private val localDataSource: LocalDataSource
) :
    CoinListContract.Repository {
    override suspend fun getCoinList(): List<Coin> {
        return dataSource.getCoinList()
    }

    override fun deleteSession() {
        localDataSource.deleteSession()
    }
}