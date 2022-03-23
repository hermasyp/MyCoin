package com.catnip.mycoin.ui.coinlist

import com.catnip.mycoin.base.arch.BaseRepositoryImpl
import com.catnip.mycoin.base.model.Resource
import com.catnip.mycoin.data.local.datasource.LocalDataSource
import com.catnip.mycoin.data.network.datasource.coin.CoinGeckoDataSource
import com.catnip.mycoin.data.network.model.response.auth.User
import com.catnip.mycoin.data.network.model.response.coin.list.Coin
import kotlinx.coroutines.Dispatchers
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


    override suspend fun getCoinList(): Resource<List<Coin>> {
        return safeApiCall(Dispatchers.IO){
            dataSource.getCoinList()
        }
    }

    override fun getUserData(): User? {
        return localDataSource.getUserData()
    }
}