package com.example.thecoin.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecoin.model.Coin
import com.example.thecoin.repository.Repository



class CoinViewModel : ViewModel() {

    val _firstCoinSelected = MutableLiveData<String>()

    val firstCoinSelected: LiveData<String> get() = _firstCoinSelected

    val _secondCoinSelected = MutableLiveData<String>()

    val secondCoinSelected: LiveData<String> get() = _secondCoinSelected

    private val _coinResult = MutableLiveData<Coin>()

    val coinResult: LiveData<Coin> get() = _coinResult

    val repository = Repository()

    val _coinConverted = MutableLiveData<Double>()

    val coinConverted: LiveData<Double> get() = _coinConverted




    lateinit var actualCoin: Coin


    fun queryCoin() {
        repository.cambioQuery(
            firstCoinSelected.value.toString(),
            secondCoinSelected.value.toString()
        ) { coinReceived ->
            coinReceived.let {
                if (coinReceived != null) {
                    _coinResult.value = coinReceived!!
                    actualCoin = coinReceived
                }
            }
        }
    }
}






