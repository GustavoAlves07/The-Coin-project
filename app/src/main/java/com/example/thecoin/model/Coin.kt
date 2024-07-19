package com.example.thecoin.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

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
    @SerializedName("varBid") var varBid: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(ask)
        parcel.writeString(bid)
        parcel.writeString(code)
        parcel.writeString(codein)
        parcel.writeString(createDate)
        parcel.writeString(high)
        parcel.writeString(low)
        parcel.writeString(name)
        parcel.writeString(pctChange)
        parcel.writeString(timestamp)
        parcel.writeString(varBid)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Coin> {
        override fun createFromParcel(parcel: Parcel): Coin {
            return Coin(parcel)
        }

        override fun newArray(size: Int): Array<Coin?> {
            return arrayOfNulls(size)
        }
    }
}
