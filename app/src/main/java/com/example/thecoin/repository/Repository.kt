package com.example.thecoin.repository

import android.util.Log
import com.example.thecoin.model.Coin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class Repository {

    private val call = Api().apiService
    private var result: Double = 0.0


    fun cambioQuery(
        firstCoinSelected: String, secondCoinSelected: String,
        callback: (Coin?) -> Unit
    ) {
        if (firstCoinSelected == secondCoinSelected) {
            val coinResult = Coin().apply {
                bid = 1.0.toString()
            }
            callback(coinResult)
        } else {
            call.coinConverter(
                firstCoinSelected,
                secondCoinSelected
            ).enqueue(object : Callback<Map<String, Coin>> {
                override fun onResponse(
                    call: Call<Map<String, Coin>>,
                    response: Response<Map<String, Coin>>
                ) {
                    if (response.isSuccessful) {
                        val currencyMap = response.body()
                        if (currencyMap != null && currencyMap.containsKey(firstCoinSelected + secondCoinSelected)) {
                            val currency = currencyMap[firstCoinSelected + secondCoinSelected]
                            currency?.let { coinReceived ->
                                val coinResult = Coin().apply {
                                    bid = coinReceived.bid
                                    ask = coinReceived.ask
                                    code = coinReceived.code
                                    codein = coinReceived.codein
                                    createDate = coinReceived.createDate
                                    high = coinReceived.high
                                    low = coinReceived.low
                                    pctChange = coinReceived.pctChange
                                    timestamp = coinReceived.timestamp
                                    varBid = coinReceived.varBid
                                    name = coinReceived.name
                                }
                                val bidValue = coinReceived.bid.toDoubleOrNull()
                                val formatedNumber = String.format(Locale.US, "%.2f", bidValue)
                                result = formatedNumber.toDoubleOrNull()!!
                                coinResult.bid = result.toString()
                                Log.i("fds","total " + coinResult.bid )
                                callback(coinResult)

                            }
                        }else{
                            callback(null)
                        }

                    }
                }


                override fun onFailure(call: Call<Map<String, Coin>>, t: Throwable) {
                    callback(null)
                    // Tratar a falha da requisição
                }
            })
        }
    }
}


