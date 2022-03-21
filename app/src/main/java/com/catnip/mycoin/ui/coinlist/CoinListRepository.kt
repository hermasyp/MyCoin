package com.catnip.mycoin.ui.coinlist

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.data.network.model.response.auth.User
import com.catnip.mycoin.data.network.model.response.coin.list.Coin
import javax.inject.Inject

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
class CoinListRepository
@Inject constructor(
    private val dataSource: CoinGeckoDataSource,
    private val localDataSource: LocalDataSource
) : BaseRepositoryImpl(),CoinListContract.Repository {


    override suspend fun getCoinList(): List<Coin> {
        return dataSource.getCoinList()
    }

    override fun getUserData(): User? {
        return localDataSource.getUserData()
    }
}