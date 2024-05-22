package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.thecoin.adapter.SpinnerAdapter
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.repository.Repository
import com.example.thecoin.viewmodel.CoinViewModel


class CurrencyExchangeFragment : Fragment() {
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!
    val coinViewModel: CoinViewModel by viewModels()
    var spinnerAdapter: SpinnerAdapter? = null
    var repository = Repository()


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


        spinnerAdapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)


        test()


        binding.button.setOnClickListener(){
            repository.cambioQuery(
                coinViewModel.firstCoinSelected,
                coinViewModel.secondCoinSelected,
                coinViewModel._queryResult)





        }



        binding.numberTest.text = coinViewModel.queryResult.toString()
        Log.e("cambio", coinViewModel.queryResult.toString())



        coinViewModel.queryResult.observe(viewLifecycleOwner, Observer { bidValue ->
            Log.e("bidValue","${coinViewModel.queryResult}")








            binding.numberTest.text = bidValue.toString()


            Toast.makeText(requireContext(), "$bidValue", Toast.LENGTH_SHORT).show()


        })





























        return binding.root


    }


    fun test() {

        coinViewModel.spinnerInit(
            binding.coinOne,
            spinnerAdapter!!,
            1,
            coinViewModel._firstCoinSelected
        )
        coinViewModel.spinnerInit(
            binding.coinTwo,
            spinnerAdapter!!,
            0,
            coinViewModel._secondCoinSelected
        )




    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}
