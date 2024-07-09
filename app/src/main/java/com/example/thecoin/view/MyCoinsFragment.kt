package com.example.thecoin.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecoin.R
import com.example.thecoin.adapter.RecyclerViewAdapter
import com.example.thecoin.databinding.FragmentMyCoinsBinding
import com.example.thecoin.model.Coin
import com.example.thecoin.viewmodel.CoinViewModel

class MyCoinsFragment : Fragment() {
    private var _binding: FragmentMyCoinsBinding? = null
    private val binding get() = _binding!!

    private lateinit var rvAdapter: RecyclerViewAdapter
    private val dataSet = mutableListOf<Coin>()
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



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun receivingCoins() {
        val coinReceived = arguments?.getSerializable("coinTransfered") as? Coin
        if (coinReceived != null) {
            val newCoin = coinReceived
            rvAdapter.addCoinToList(newCoin)
        } else {
            // Trate o caso em que coinReceived é nulo
            // Por exemplo, você pode mostrar uma mensagem de erro ou logar um aviso
        }
    }


}