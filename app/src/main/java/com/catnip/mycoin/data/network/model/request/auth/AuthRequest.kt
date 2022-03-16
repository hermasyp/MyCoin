package com.catnip.mycoin.data.network.model.request.auth

import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class AuthRequest(
    @SerializedName("email")
    var email: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("password")
    var password: String?,
)
