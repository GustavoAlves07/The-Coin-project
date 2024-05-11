package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.model.Coin
import com.example.thecoin.repository.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrencyExchangeFragment : Fragment() {


    var result: Double? = null
    var firstCoinSelected: String? = null
    var secondCoinSelected: String? = null

    val text: String = ""

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



        convert()

        val spinnerFirstCoin = binding.coinOne
        val spinnerSecondCoin = binding.coinTwo

        spinnerFirstCoin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: String = parent?.getItemAtPosition(position).toString()
                firstCoinSelected = spinnerFirstCoin.selectedItem.toString()
                Log.e("testeMoeda", firstCoinSelected!!)

                Toast.makeText(requireContext(), "selected item : " + item, Toast.LENGTH_SHORT)
                    .show()


            }


            override fun onNothingSelected(parent: AdapterView<*>?) {

            }


        }





        spinnerSecondCoin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: String = parent?.getItemAtPosition(position).toString()
                secondCoinSelected = spinnerSecondCoin.selectedItem.toString()
                Log.e("moedaDois", secondCoinSelected!!)
                Toast.makeText(requireContext(), "selected item : " + item, Toast.LENGTH_SHORT)
                    .show()


            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }


        }

        val moedas: ArrayList<String> = arrayListOf()


        moedas.add("USD")
        moedas.add("EUR")
        moedas.add("BRL")
        moedas.add("caguei")


        val orderedArray = moedas.sorted()

        val adapterFirstCoin =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                orderedArray
            )
        adapterFirstCoin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFirstCoin.adapter = adapterFirstCoin

        val adapterSecondCoin =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                orderedArray
            )
        adapterSecondCoin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSecondCoin.adapter = adapterSecondCoin

        return binding.root


    }


    private fun cambioQuery() {
        if (firstCoinSelected != null && secondCoinSelected != null) {
            call.coinConverter(firstCoinSelected!!, secondCoinSelected!!)
                .enqueue(object : Callback<Map<String, Coin>> {
                    override fun onResponse(
                        call: Call<Map<String, Coin>>,
                        response: Response<Map<String, Coin>>
                    ) {
                        if (response.isSuccessful) {
                            val currencyMap = response.body()

                            if (currencyMap != null && currencyMap.containsKey(firstCoinSelected + secondCoinSelected)) {
                                val currency = currencyMap[firstCoinSelected + secondCoinSelected]


                                val bidValue = currency!!.bid.toDoubleOrNull()

                                if (bidValue != null) {
                                    Log.i("objetocoin", "esse é o valor recebido = $bidValue")



                                    result = bidValue
                                    Log.i("firstResult", result.toString())

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

    }


    private fun convert() {

     val cambioMaker =   binding.button.setOnClickListener() {
            val inputUser = binding.inputUser.text.toString().toDoubleOrNull()
            cambioQuery()

            if (result != null && inputUser != null) {
                val calc = inputUser * result!!

                val formatedCalc = String.format("%.2f", calc)

                

                binding.numberTest.text = formatedCalc

                Log.e("result", formatedCalc)
            } else {

                Toast.makeText(
                    requireContext(),
                    "Invalid input or result not available",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        return cambioMaker

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
