package com.catnip.mycoin.base.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/

class GenericViewModelFactory(private val viewModel: ViewModel) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(viewModel::class.java)){
            return viewModel as T
        }
        throw IllegalArgumentException("Unknown Class Name")
    }

}