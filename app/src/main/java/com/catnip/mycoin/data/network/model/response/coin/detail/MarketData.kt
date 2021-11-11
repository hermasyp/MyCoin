package com.catnip.mycoin.data.network.model.response.coin.detail


import com.google.gson.annotations.SerializedName

data class MarketData(
    @SerializedName("current_price")
    var currentPrice: CurrentPrice? = null
)