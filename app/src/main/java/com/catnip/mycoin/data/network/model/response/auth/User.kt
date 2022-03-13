package com.catnip.mycoin.data.network.model.response.auth

import com.google.gson.annotations.SerializedName

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
data class User(
    @SerializedName("_id")
    var id: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("photo")
    var photo: String?,
    @SerializedName("token")
    var token: String?
)