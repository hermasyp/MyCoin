package com.catnip.mycoin.base.arch

import android.util.Log
import androidx.lifecycle.ViewModel

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
open class BaseViewModelImpl : ViewModel(), BaseContract.BaseViewModel {
    override fun logResponse(msg: String?) {
        Log.d(BaseViewModelImpl::class.java.simpleName, msg.orEmpty())
    }
}