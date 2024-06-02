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


    val call = Api().apiService

    fun cambioQuery(
        firstCoinSelected: LiveData<String>, secondCoinSelected: LiveData<String>,
        result: MutableLiveData<Double>
    ) {
        if (firstCoinSelected.value == secondCoinSelected.value) {
            result.value = 1.0

        } else {
            call.coinConverter(
                firstCoinSelected.value.toString(),
                secondCoinSelected.value.toString()
            )
                .enqueue(object : Callback<Map<String, Coin>> {
                    override fun onResponse(
                        call: Call<Map<String, Coin>>,
                        response: Response<Map<String, Coin>>
                    ) {
                        if (response.isSuccessful) {

                            val currencyMap = response.body()

                            if (currencyMap != null && currencyMap.containsKey(firstCoinSelected.value + secondCoinSelected.value)) {
                                val currency =
                                    currencyMap[firstCoinSelected.value + secondCoinSelected.value]


                                val bidValue = currency!!.bid.toDoubleOrNull()

                                val formatedNumber = String.format(Locale.US,"%.2f",bidValue)


                                result.value = formatedNumber.toDoubleOrNull()



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

}




