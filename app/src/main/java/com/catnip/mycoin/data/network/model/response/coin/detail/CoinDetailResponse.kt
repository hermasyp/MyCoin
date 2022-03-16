package com.catnip.mycoin.data.network.model.response.coin.detail


import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    @SerializedName("categories")
    var categories: List<String>? = null,
    @SerializedName("description")
    var description: Description? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("image")
    var image: Image? = null,
    @SerializedName("market_data")
    var marketData: MarketData? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null
)