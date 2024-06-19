package com.example.thecoin.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.thecoin.R
import com.example.thecoin.adapter.RecyclerViewAdapter
import com.example.thecoin.databinding.FragmentMyCoinsBinding
import com.example.thecoin.model.Coin

class MyCoinsFragment : Fragment() {
    private var _binding: FragmentMyCoinsBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCoinsBinding.inflate(inflater, container, false)

        val dataSet = mutableListOf(
            Coin(
                "50000",
                "49500",
                "BTC",
                "BRL",
                "2024-06-18T14:00:00",
                "50500",
                "49000",
                "Bitcoin",
                "1.0",
                "1624024800",
                "500"
            ),Coin(
                "50000",
                "49500",
                "BTC",
                "BRL",
                "2024-06-18T14:00:00",
                "50500",
                "49000",
                "Bitcoin",
                "1.0",
                "1624024800",
                "500"
            ),Coin(
                "50000",
                "49500",
                "BTC",
                "BRL",
                "2024-06-18T14:00:00",
                "50500",
                "49000",
                "Bitcoin",
                "1.0",
                "1624024800",
                "500"
            ),Coin(
                "50000",
                "49500",
                "BTC",
                "BRL",
                "2024-06-18T14:00:00",
                "50500",
                "49000",
                "Bitcoin",
                "1.0",
                "1624024800",
                "500"
            ),Coin(
                "50000",
                "49500",
                "BTC",
                "BRL",
                "2024-06-18T14:00:00",
                "50500",
                "49000",
                "Bitcoin",
                "1.0",
                "1624024800",
                "500"
            ),Coin(
                "50000",
                "49500",
                "BTC",
                "BRL",
                "2024-06-18T14:00:00",
                "50500",
                "49000",
                "Bitcoin",
                "1.0",
                "1624024800",
                "500"
            )
        )
            val rvAdapter = RecyclerViewAdapter (dataSet)

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


}