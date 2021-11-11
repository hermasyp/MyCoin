package com.catnip.mycoin.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class BaseAuthResponse<D,E>(
    @SerializedName("success")
    val isSuccess : Boolean,
    @SerializedName("data")
    val data : D,
    @SerializedName("errors")
    val errorMsg : E
)

// BaseAuthResponse<UserData,String>
// BaseAuthResponse<List<HistoryGame>,String>