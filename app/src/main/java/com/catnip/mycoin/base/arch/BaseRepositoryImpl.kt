package com.catnip.mycoin.base.arch

import android.util.Log
import com.catnip.mycoin.base.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseRepositoryImpl : BaseContract.BaseRepository {
    override fun logResponse(msg: String?) {
        Log.d(BaseRepositoryImpl::class.java.simpleName, msg.orEmpty())
    }

    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): Resource<T> {
        return withContext(dispatcher) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> Resource.Error("No Internet Connection")
                    is HttpException -> {
                        val code = throwable.code()
                        if(code == 422){
                            Resource.Error("Wrong data")
                        }else{
                            Resource.Error("Something error in data")
                        }
                    }
                    else -> {
                        Resource.Error("Something error in data")
                    }
                }
            }
        }
    }
}