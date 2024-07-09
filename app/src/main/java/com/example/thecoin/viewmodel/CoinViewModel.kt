package com.example.thecoin.viewmodel

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.thecoin.R
import com.example.thecoin.adapter.RecyclerViewAdapter
import com.example.thecoin.model.Coin
import com.example.thecoin.repository.Repository
import com.example.thecoin.view.MyCoinsFragment
import java.util.ArrayList
import java.util.Locale

class CoinViewModel : ViewModel() {

    val _firstCoinSelected = MutableLiveData<String>()

    val firstCoinSelected: LiveData<String> get() = _firstCoinSelected

    val _secondCoinSelected = MutableLiveData<String>()

    val secondCoinSelected: LiveData<String> get() = _secondCoinSelected

    private val _coinResult = MutableLiveData<Coin>()

    val coinResult: LiveData<Coin> get() = _coinResult

    val _queryResult = MutableLiveData<Double>()

    val repository = Repository()

    val queryResult: LiveData<Double> get() = _queryResult

    val _coinConverted = MutableLiveData<Double>()

    val coinConverted: LiveData<Double> get() = _coinConverted

    private lateinit var actualCoin: Coin

    private val coinList = arrayListOf<Coin>()

    private val myCoinsFragment = MyCoinsFragment()

    fun queryCoin() {
        repository.cambioQuery(
            firstCoinSelected.value.toString(),
            secondCoinSelected.value.toString()
        ) { coinReceived ->
            coinReceived.let {
                if (coinReceived != null) {
                    _coinResult.value = coinReceived!!
                    Log.i("fds2","total " + _coinResult.value!!.bid )
                }
            }
        }
    }

    fun conversionFormatValue(inputUser:String,coinBid:Double) : Double{
        val formatedNumber = String.format(Locale.US, "%.2f", inputUser.toString().toDouble() * coinBid)

        return formatedNumber.toDouble()



    }


    fun receivingCoinList(
        coinList: ArrayList<Coin>,
        lifecycleOwner: LifecycleOwner,
        recyclerViewAdapter: RecyclerViewAdapter
    ) {
    }


    fun transferCoinsFragments(fragmentManager: FragmentManager) {
//        val myCoinsFragment = MyCoinsFragment()
//        val coinTransferBundle = Bundle()
//        coinResult.value?.let { coin ->
//            coinTransferBundle.putSerializable("coinTransfered", coin)
//        }
//        myCoinsFragment.arguments = coinTransferBundle
//
//        fragmentManager.beginTransaction()
//            .replace(R.id.my_coin_fragment, myCoinsFragment)
//            .addToBackStack(null)
//            .commit()
    }


}



