package com.example.thecoin.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.thecoin.R
import com.example.thecoin.databinding.MycoinAdapterRvBinding
import com.example.thecoin.model.Coin

class RecyclerViewAdapter(private val myCoinsList: MutableList<Coin>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    // ViewHolder class
    class ViewHolder(private val bindingView: MycoinAdapterRvBinding) :
        RecyclerView.ViewHolder(bindingView.root) {

        fun bind(coin: Coin) {
            bindingView.value.text = coin.bid
            bindingView.coinsNames.text = coin.name

            bindingView.buttonGroup.setOnCheckedChangeListener { _, checkid ->
                when (checkid) {
                    R.id.btnActualValue -> bindingView.value.text = coin.bid
                    R.id.btnMaximumValue -> bindingView.value.text = coin.high
                    R.id.btnMinimumValue -> bindingView.value.text = coin.low
                    R.id.btnVariation -> bindingView.value.text = coin.varBid
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MycoinAdapterRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val coin = myCoinsList[position]
        holder.bind(coin)
    }

    override fun getItemCount(): Int = myCoinsList.size

    fun addCoinToList(coin: Coin) {
        myCoinsList.add(coin)
        notifyItemInserted(myCoinsList.size - 1)
        Log.i("listSize", myCoinsList.size.toString())
    }
}
