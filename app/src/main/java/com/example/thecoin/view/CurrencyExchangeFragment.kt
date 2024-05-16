package com.example.thecoin.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.example.thecoin.adapter.SpinnerAdapter
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

    var itemCopyOne: String = ""
    var itemCopyTwo: String = ""
    var adapterFirstCoin: SpinnerAdapter? = null
    var adapterSecondCoin: SpinnerAdapter? = null

    val text: String = ""

    val call = Api().apiService
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!

    lateinit var spinnerFirstCoin: Spinner
    lateinit var spinnerSecondCoin: Spinner
    lateinit var inputUser: EditText


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

        inputUser = binding.inputUser

        spinnerFirstCoin = binding.coinOne
        spinnerSecondCoin = binding.coinTwo
        val adapterFirstCoin: SpinnerAdapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)
        val adapterSecondCoin: SpinnerAdapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)


        spinnerInit(
            spinnerFirstCoin,
            adapterFirstCoin,
            1,
            { firstCoinSelected = it },
            { itemCopyOne = it })
        spinnerInit(
            spinnerSecondCoin,
            adapterSecondCoin,
            0,
            { secondCoinSelected = it },
            { itemCopyTwo = it })


        inputUser.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                cambioQuery()

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                convert()
            }

            override fun afterTextChanged(s: Editable?) {
               convert()
            }


        })







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

        if (inputUser.text.isNotEmpty()) {


            if (result != null) {
                cambioQuery()
                val calc = inputUser.text.toString().toDouble() * result!!

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

    }

    private fun inverterCoins(
        firstSpinner: Spinner,
        secondSpinner: Spinner
    ) {

        binding.invertArrow.setOnClickListener() {
            firstSpinner.setSelection(SpinnerAdapter.listCoin.indexOf(itemCopyTwo))
            secondSpinner.setSelection(SpinnerAdapter.listCoin.indexOf(itemCopyOne))


        }


    }

    private fun spinnerInit(
        spinner: Spinner,
        spinnerAdapter: SpinnerAdapter,
        positionCoin: Int,
        coinSelectedCallback: (String) -> Unit,
        coinInversorCallback: (String) -> Unit,
    ) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: String = parent?.getItemAtPosition(position).toString()
                coinSelectedCallback(item)
                coinInversorCallback(item)
                Log.e("moedaDois", item)
                Toast.makeText(requireContext(), "selected item : $item", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Implementação necessária, mas pode ficar vazia se não for necessário fazer nada.
            }
        }


        spinner.adapter = spinnerAdapter

        spinner.setSelection(positionCoin)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        inverterCoins(spinnerFirstCoin, spinnerSecondCoin)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
