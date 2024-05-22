package com.example.thecoin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecoin.model.Coin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository {

    val call = Api().apiService

    fun cambioQuery(
        firstCoinSelected: LiveData<String>, secondCoinSelected: LiveData<String>,
        result: MutableLiveData<Double>
    ) {

        call.coinConverter(firstCoinSelected.toString(), secondCoinSelected.toString())
            .enqueue(object : Callback<Map<String, Coin>> {
                override fun onResponse(
                    call: Call<Map<String, Coin>>,
                    response: Response<Map<String, Coin>>
                ) {
                    if (response.isSuccessful) {
                        val coinName =
                            firstCoinSelected.value.toString() + secondCoinSelected.value.toString()
                        val currencyMap = response.body()

                        if (currencyMap != null && currencyMap.containsKey(coinName)) {
                            val currency = currencyMap[coinName]


                            val bidValue = currency!!.bid.toDoubleOrNull()


                            result.value = bidValue!!.toDouble()


                            Log.i("objetocoin", "esse é o valor recebido = $bidValue")

                        }
                    }
                }


                override fun onFailure(call: Call<Map<String, Coin>>, t: Throwable) {

                }
            })

        return

    }


}