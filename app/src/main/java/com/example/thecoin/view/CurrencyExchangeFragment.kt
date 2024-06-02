package com.example.thecoin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.thecoin.adapter.SpinnerAdapter
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.repository.Repository
import com.example.thecoin.viewmodel.CoinViewModel


class CurrencyExchangeFragment : Fragment() {
    private var _binding: FragmentCurrencyExchangeBinding? = null
    private val binding get() = _binding!!
    val coinViewModel: CoinViewModel by viewModels()
    var spinnerAdapter: SpinnerAdapter? = null


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

        coinViewModel.firstCoinSelected.observe(viewLifecycleOwner) { firstCoinSelection ->
            coinViewModel.queryCoin()
            coinViewModel.convertCoin(binding.inputUser,viewLifecycleOwner)



        }

        coinViewModel.secondCoinSelected.observe(viewLifecycleOwner) { secondCoinSelection ->
            coinViewModel.queryCoin()
            coinViewModel.convertCoin(binding.inputUser,viewLifecycleOwner)




        }


        coinViewModel.queryResult.observe(viewLifecycleOwner) {
            binding.numberTest.text = it.toString()
        }




        spinnerAdapter =
            SpinnerAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item)





        initSelector()



































        return binding.root


    }


    fun initSelector() {

        coinViewModel.initSpinner(
            binding.coinOne,
            spinnerAdapter!!,
            1,
            coinViewModel._firstCoinSelected
        )
        coinViewModel.initSpinner(
            binding.coinTwo,
            spinnerAdapter!!,
            0,
            coinViewModel._secondCoinSelected
        )


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
