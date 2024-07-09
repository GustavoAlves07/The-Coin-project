package com.example.thecoin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Coin(
    @SerializedName("ask") var ask: String = "",
    @SerializedName("bid") var bid: String = "",
    @SerializedName("code") var code: String = "",
    @SerializedName("codein") var codein: String = "",
    @SerializedName("create_date") var createDate: String = "",
    @SerializedName("high") var high: String = "",
    @SerializedName("low") var low: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("pctChange") var pctChange: String = "",
    @SerializedName("timestamp") var timestamp: String = "",
    @SerializedName("varBid") var varBid: String = "",



    ):Serializable

