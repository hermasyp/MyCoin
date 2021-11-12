package com.catnip.mycoin.data.network.model.response.coin


import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("current_price")
    var currentPrice: Double? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("image")
    var image: String? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null
)