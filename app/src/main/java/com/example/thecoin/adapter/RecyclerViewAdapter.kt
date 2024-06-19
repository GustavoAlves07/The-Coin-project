package com.example.thecoin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecoin.R
import com.example.thecoin.databinding.MycoinAdapterRvBinding
import com.example.thecoin.model.Coin

class RecyclerViewAdapter(private val myCoinsList: MutableList<Coin>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    var _binding: MycoinAdapterRvBinding? = null

    val binding get() = _binding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        _binding =
            MycoinAdapterRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)



        return ViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = myCoinsList[position]
        holder.bind(coin)

        holder.radioGroup.setOnCheckedChangeListener { group, checkid ->
            when (checkid) {

                R.id.btnActualValue -> {
                    coin


                }

                R.id.btnMaximumValue -> {


                }

                R.id.btnMinimumValue -> {


                }

                R.id.btnVariation -> {


                }


            }
        }

    }

    override fun getItemCount(): Int {
        return myCoinsList.size
    }

    class ViewHolder(private val bindingView: MycoinAdapterRvBinding) :
        RecyclerView.ViewHolder(bindingView.root) {
        fun bind(coin: Coin) {
            bindingView.value.text = coin.bid
            bindingView.coinsNames.text = coin.name
        }

        val radioGroup = bindingView.buttonGroup
        val actualValue = bindingView.btnActualValue
        val maximumValue = bindingView.btnMaximumValue
        val minimumValue = bindingView.btnMinimumValue
        val variationValue = bindingView.btnVariation


    }
}