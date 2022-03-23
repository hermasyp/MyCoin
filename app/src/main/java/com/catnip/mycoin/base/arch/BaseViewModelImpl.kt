package com.catnip.mycoin.base.arch

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catnip.mycoin.base.model.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseViewModelImpl : ViewModel(), BaseContract.BaseViewModel {
    override fun logResponse(msg: String?) {
        Log.d(BaseViewModelImpl::class.java.simpleName, msg.orEmpty())
    }
}