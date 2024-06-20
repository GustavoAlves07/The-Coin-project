package com.example.thecoin.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.thecoin.model.Coin
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Locale

class Repository {

    private val call = Api().apiService


    fun cambioQuery(
        firstCoinSelected: String, secondCoinSelected: String, coinResult: MutableLiveData<Coin>,
        result: MutableLiveData<Double>
    ) {
        if (firstCoinSelected == secondCoinSelected) {
            result.value = 1.0
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
                            currency?.let {
                                coinResult.value = it

                                val bidValue = it.bid.toDoubleOrNull()
                                val formatedNumber = String.format(Locale.US, "%.2f", bidValue)
                                result.value = formatedNumber.toDoubleOrNull()

                                Log.i("objetocoin", "esse é o valor recebido = $bidValue")
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<Map<String, Coin>>, t: Throwable) {
                    // Tratar a falha da requisição
                }
            })
        }
    }
}


