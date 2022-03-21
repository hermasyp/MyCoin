package com.catnip.mycoin.data.network.model.response.auth


import com.google.gson.annotations.SerializedName

data class BaseAuthResponse<D,E>(
    @SerializedName("data")
    var data: D,
    @SerializedName("success")
    var isSuccess: Boolean,
    @SerializedName("errors")
    var errors: E
)

