package com.catnip.mycoin.data.network.model.response.coin.detail


import com.google.gson.annotations.SerializedName

data class Image(
    @SerializedName("large")
    var large: String? = null,
    @SerializedName("small")
    var small: String? = null,
    @SerializedName("thumb")
    var thumb: String? = null
)