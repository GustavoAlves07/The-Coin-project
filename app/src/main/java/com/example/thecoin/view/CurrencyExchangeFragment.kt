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
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.thecoin.adapter.SpinnerAdapter
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.viewmodel.CoinViewModel
import java.util.Locale

class CurrencyExchangeFragment : Fragment() {
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!
    private val coinViewModel: CoinViewModel by viewModels()
    var spinnerAdapter: SpinnerAdapter? = null
    lateinit var spinnerOne: Spinner
    lateinit var spinnerTwo: Spinner
    private val initialValue = 1.0
    private val myCoinsFragment = MyCoinsFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrencyExchangeBinding.inflate(inflater, container, false)
        spinnerOne = binding.spinnerFirstCoin
        spinnerTwo = binding.spinnerSecondCoin
        spinnerAdapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)


        initSelector()
        initViewModel()



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinViewModel.coinConverted.observe(viewLifecycleOwner) { convertedCoin ->
            binding.numberTest.text = convertedCoin.toString()
        }

        convertCoin(binding.inputUser)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViewModel() {
        coinViewModel.firstCoinSelected.observe(viewLifecycleOwner) { firstCoinSelection ->
            coinViewModel.queryCoin()
        }
        coinViewModel.secondCoinSelected.observe(viewLifecycleOwner) { secondCoinSelection ->
            coinViewModel.queryCoin()
        }
        coinViewModel.coinResult.observe(viewLifecycleOwner) { coinQuery ->
            binding.numberTest.text = coinQuery.bid
        }
        binding.invertArrow.setOnClickListener {
            invertCoin(spinnerOne, spinnerTwo)
        }
        binding.saveButton.setOnClickListener {
        }
    }

    fun convertCoin(editText: EditText) {

        editText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                    val initialInputUser = editText.text.toString().toDoubleOrNull()

                    val initialConversionRate =
                        coinViewModel.coinResult.value!!.bid.toDoubleOrNull()

                    val initialFormatation = initialInputUser?.times(initialConversionRate!!)

                    coinViewModel._coinConverted.value = initialFormatation


                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    coinViewModel.coinResult.observe(viewLifecycleOwner) {
                        val inputValue = editText.text.toString().toDoubleOrNull() ?: 0.0
                        val conversionRate =
                            coinViewModel.coinResult.value!!.bid.toDoubleOrNull() ?: 1.0
                        val formatedNumber =
                            String.format(Locale.US, "%.2f", inputValue * conversionRate)
                        coinViewModel._coinConverted.value = formatedNumber.toDoubleOrNull()

                    }


                }

                override fun afterTextChanged(s: Editable?) {

                }


            })


    }

    private fun initSelector() {
        initSpinner(spinnerOne, spinnerAdapter!!, 1, coinViewModel._firstCoinSelected)
        initSpinner(spinnerTwo, spinnerAdapter!!, 0, coinViewModel._secondCoinSelected)
    }

    private fun initSpinner(
        spinner: Spinner,
        spinnerAdapter: SpinnerAdapter,
        initialCoinPosition: Int,
        coinSelected: MutableLiveData<String>
    ) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: String = parent?.getItemAtPosition(position).toString()
                Log.i("atual", position.toString())
                coinSelected.value = item
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        spinner.adapter = spinnerAdapter
        spinner.setSelection(initialCoinPosition)
    }

    private fun invertCoin(spinnerOne: Spinner, spinnerTwo: Spinner) {
        val positionOne = spinnerOne.selectedItemPosition
        val positionTwo = spinnerTwo.selectedItemPosition
        spinnerOne.setSelection(positionTwo)
        spinnerTwo.setSelection(positionOne)
    }
}
