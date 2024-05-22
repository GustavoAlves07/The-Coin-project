package com.example.thecoin.adapter

import android.content.Context
import android.widget.ArrayAdapter

val coinList:MutableList<String> = mutableListOf("BRL","USD","EUR")

class SpinnerAdapter(context: Context,layout : Int,list: MutableList<String> = (coinList)) : ArrayAdapter<String>(context,layout,list) {

    companion object {
        val listCoin = coinList
    }


}