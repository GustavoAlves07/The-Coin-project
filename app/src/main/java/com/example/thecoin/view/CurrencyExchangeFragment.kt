package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.example.thecoin.adapter.SpinnerAdapter
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.viewmodel.CoinViewModel


class CurrencyExchangeFragment : Fragment() {
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!
    val coinViewModel: CoinViewModel by viewModels()
    var spinnerAdapter: SpinnerAdapter? = null
    lateinit var spinnerOne : Spinner
    lateinit var spinnerTwo: Spinner


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            FragmentCurrencyExchangeBinding.inflate(
                inflater,
                container,
                false
            )

        spinnerOne = binding.spinnerFirstCoin
        spinnerTwo = binding.spinnerSecondCoin

        spinnerAdapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)

        initSelector()
        initViewModel()





        return binding.root
    }


    fun initSelector() {

        initSpinner(
            spinnerOne,
            spinnerAdapter!!,
            1, coinViewModel._positionCoinOne,
            coinViewModel._firstCoinSelected
        )
        initSpinner(
            spinnerTwo,
            spinnerAdapter!!,
            0, coinViewModel._positionCoinTwo,
            coinViewModel._secondCoinSelected
        )

    }

    fun initSpinner(
        spinner: Spinner,
        spinnerAdapter: SpinnerAdapter,
        initiCoinPosition: Int,
        invertCoinPosition: MutableLiveData<Int>,
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
                Log.i("atual",position.toString())
                coinSelected.value = item
                invertCoinPosition.value = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
        spinner.adapter = spinnerAdapter
        spinner.setSelection(initiCoinPosition)
    }

    private fun initViewModel() {
        coinViewModel.firstCoinSelected.observe(viewLifecycleOwner) { firstCoinSelection ->
            coinViewModel.queryCoin()
            coinViewModel.convertCoin(binding.inputUser, viewLifecycleOwner)
        }
        coinViewModel.secondCoinSelected.observe(viewLifecycleOwner) { secondCoinSelection ->
            coinViewModel.queryCoin()
            coinViewModel.convertCoin(binding.inputUser, viewLifecycleOwner)
        }
        coinViewModel.queryResult.observe(viewLifecycleOwner) { queryResult ->
            binding.numberTest.text = queryResult.toString()
        }
        binding.invertArrow.setOnClickListener(){
            coinViewModel.invertCoin(spinnerOne,spinnerTwo)
        }


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coinViewModel.coinConverted.observe(viewLifecycleOwner) { convertedCoin ->
            binding.numberTest.text = convertedCoin.toString()
        }



    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
