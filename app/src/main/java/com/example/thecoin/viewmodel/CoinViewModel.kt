package com.example.thecoin.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecoin.model.Coin

class CoinViewModel : ViewModel() {

    val coinViewModel: MutableLiveData<Coin> = MutableLiveData()




}