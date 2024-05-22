package com.example.thecoin.viewmodel

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecoin.adapter.SpinnerAdapter
import com.example.thecoin.repository.Repository

class CoinViewModel : ViewModel() {

    val _firstCoinSelected = MutableLiveData<String>()

    val firstCoinSelected : LiveData<String> get() = _firstCoinSelected

    val _secondCoinSelected = MutableLiveData<String>()

    val secondCoinSelected : LiveData<String> get() = _secondCoinSelected

     val _queryResult = MutableLiveData<Double>()

    val repository = Repository()

    val queryResult : LiveData<Double> get() = _queryResult



     fun spinnerInit(
        spinner: Spinner,
        spinnerAdapter: SpinnerAdapter,
        positionCoin: Int,
        coinSelected:MutableLiveData<String>) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val item: String = parent?.getItemAtPosition(position).toString()
                coinSelected.value = item

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }


        spinner.adapter = spinnerAdapter

        spinner.setSelection(positionCoin)







    }

    fun convertCoin(){


    }






}