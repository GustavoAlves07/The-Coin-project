package com.example.thecoin.viewmodel

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thecoin.adapter.SpinnerAdapter
import com.example.thecoin.databinding.FragmentCurrencyExchangeBinding
import com.example.thecoin.repository.Repository
import java.util.Locale

class CoinViewModel : ViewModel() {

    val _firstCoinSelected = MutableLiveData<String>()

    val firstCoinSelected: LiveData<String> get() = _firstCoinSelected

    val _secondCoinSelected = MutableLiveData<String>()

    val secondCoinSelected: LiveData<String> get() = _secondCoinSelected

    val _queryResult = MutableLiveData<Double>()

    val repository = Repository()

    val queryResult: LiveData<Double> get() = _queryResult


    val _coinConverted = MutableLiveData<Double>()

    val coinConverted: LiveData<Double> get() = _coinConverted

    fun queryCoin() {

        repository.cambioQuery(
            firstCoinSelected,
            secondCoinSelected,
            _queryResult
        )


    }


    fun initSpinner(
        spinner: Spinner,
        spinnerAdapter: SpinnerAdapter,
        positionCoin: Int,
        coinSelected: MutableLiveData<String>
    ) {
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


    fun convertCoin(editText: EditText, viewLifecycleOwner: LifecycleOwner) {

        editText.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {


                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    queryResult.observe(viewLifecycleOwner) {
                        val inputValue = editText.text.toString().toDoubleOrNull() ?: 0.0
                        val conversionRate = queryResult.value ?: 1.0
                        val formatedNumber =
                            String.format(Locale.US, "%.2f", inputValue * conversionRate)
                        _coinConverted.value = formatedNumber.toDoubleOrNull()

                    }


                }

                override fun afterTextChanged(s: Editable?) {

                }


            })


    }


}



