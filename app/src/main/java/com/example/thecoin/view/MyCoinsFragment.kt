package com.example.thecoin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecoin.adapter.RecyclerViewAdapter
import com.example.thecoin.databinding.FragmentMyCoinsBinding
import com.example.thecoin.model.Coin
import com.example.thecoin.viewmodel.CoinViewModel

class MyCoinsFragment : Fragment() {
    private var _binding: FragmentMyCoinsBinding? = null
    private val binding get() = _binding!!
    private val coinViewModel: CoinViewModel by viewModels()
    private lateinit var rvAdapter: RecyclerViewAdapter
    private val dataSet = ArrayList<Coin>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCoinsBinding.inflate(inflater, container, false)

        rvAdapter = RecyclerViewAdapter(dataSet)
        val recyclerView = binding.rvMyCoins

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = rvAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        receivingCoin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun receivingCoin() {
       parentFragmentManager.setFragmentResultListener("actualCoinAdded",this){_,bundle ->
           val actualCoin: Coin? = bundle.getParcelable("actualCoin")
           val sameCoinTest = dataSet.any { it.name == actualCoin?.name }

           if (!sameCoinTest){actualCoin?.let {
               rvAdapter.addCoinToList(actualCoin)}
           }else{
               Toast.makeText(requireContext(), "Moeda atual Ja adiciona na lista", Toast.LENGTH_SHORT).show()
           }
       }

    }


}
