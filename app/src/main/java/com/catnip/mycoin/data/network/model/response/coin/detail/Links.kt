package com.catnip.mycoin.data.network.model.response.coin.detail


import com.google.gson.annotations.SerializedName

data class Links(
    @SerializedName("homepage")
    var homepage: List<String>? = null
)