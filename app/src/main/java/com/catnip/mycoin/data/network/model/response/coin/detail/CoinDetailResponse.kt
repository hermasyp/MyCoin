package com.catnip.mycoin.data.network.model.response.coin.detail


import com.google.gson.annotations.SerializedName

data class CoinDetailResponse(
    @SerializedName("additional_notices")
    var additionalNotices: List<Any>? = null,
    @SerializedName("categories")
    var categories: List<String>? = null,
    @SerializedName("description")
    var description: Description? = null,
    @SerializedName("id")
    var id: String? = null,
    @SerializedName("image")
    var image: Image? = null,
    @SerializedName("last_updated")
    var lastUpdated: String? = null,
    @SerializedName("links")
    var links: Links? = null,
    @SerializedName("market_data")
    var marketData: MarketData? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("symbol")
    var symbol: String? = null
)