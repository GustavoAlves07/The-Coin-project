package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.thecoin.R
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.model.Coin
import com.example.thecoin.repository.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyExchangeFragment : Fragment() {


    var result: Double? = null
    val coinOne = "USD"
    val coinTwo = "BRL"

    val call = Api().apiService
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentCurrencyExchangeBinding.inflate(
                LayoutInflater.from(requireContext()),
                container,
                false
            )

        convertCoin()

        convert()

        val spinnerFirstCoin = binding.coinOne

        spinnerFirstCoin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: String = parent?.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "selected item : " + item, Toast.LENGTH_SHORT)
                    .show()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }

        val arrayList: ArrayList<String> = arrayListOf("Seila")


        arrayList.add("seilá")
        arrayList.add("nãosei")
        arrayList.add("vamolá")
        arrayList.add("caguei")

        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, arrayList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFirstCoin.adapter = adapter

        return binding.root





    }


    private fun convertCoin() {
        call.coinConverter(coinOne, coinTwo).enqueue(object : Callback<Map<String, Coin>> {
            override fun onResponse(
                call: Call<Map<String, Coin>>,
                response: Response<Map<String, Coin>>
            ) {
                if (response.isSuccessful) {
                    val currencyMap = response.body()

                    if (currencyMap != null && currencyMap.containsKey(coinOne + coinTwo)) {
                        val currency = currencyMap[coinOne + coinTwo]


                        val bidValue = currency!!.bid.toDoubleOrNull()

                        if (bidValue != null) {
                            Log.i("objetocoin", "esse é o valor recebido = $bidValue")
                            val formatedCalc = String.format("%.2f", bidValue)
                            binding.numberTest.text = formatedCalc

                            result = bidValue

                            binding.numberTest.isVisible = true
                        } else {
                            Log.e(
                                "convertCoin",
                                "Valor do lance (bid) não está disponível para a moeda "
                            )
                        }
                    } else {
                        Log.e("convertCoin", "Resposta não contém a moeda ")
                    }
                } else {
                    Log.e("convertCoin", "Erro na requisição: ${response.code()}")
                }

            }

            override fun onFailure(call: Call<Map<String, Coin>>, t: Throwable) {
                Log.e("convertCoin", "Falha na requisição: ${t.message}")
                Toast.makeText(
                    requireContext(),
                    "Falha na requisição: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun convert() {
        binding.button.setOnClickListener() {
            val inputUser = binding.inputUser.text.toString().toDoubleOrNull()

            if (result != null && inputUser != null) {
                val calc = inputUser * result!!

                val formatedCalc = String.format("%.2f", calc)

                binding.numberTest.isVisible = true

                binding.numberTest.text = formatedCalc
            } else {

                Toast.makeText(
                    requireContext(),
                    "Invalid input or result not available",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
