package com.catnip.mycoin.data.network.model.response.auth


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    var id: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("username")
    var username: String?,
    @SerializedName("token")
    var token: String?,
    @SerializedName("photo")
    var photo: String?,
)

