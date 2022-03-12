package com.catnip.mycoin.base.arch

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
interface BaseContract {
    interface BaseView {
        fun observeData()
        fun showContent(isVisible: Boolean)
        fun showLoading(isVisible: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String? = null)
    }

    interface BaseViewModel {
        fun logResponse(msg : String?)
    }

    interface BaseRepository {
        fun logResponse(msg : String?)
    }
}