package com.catnip.mycoin.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class UserData(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("token")
    val token: String?
)