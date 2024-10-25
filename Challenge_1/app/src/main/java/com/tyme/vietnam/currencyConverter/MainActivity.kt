package com.tyme.vietnam.currencyConverter

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import com.tyme.vietnam.currencyConverter.adapter.CurrencySpinnerAdapter
import com.tyme.vietnam.currencyConverter.api.RetrofitClient
import com.tyme.vietnam.currencyConverter.model.ExchangeRates

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var edtInput : EditText
    private lateinit var spinner: Spinner
    private lateinit var btnConvert : Button
    private lateinit var tvBaseCurrency : TextView
    private lateinit var tvShowError : TextView
    private lateinit var tvConvertedCurrency : TextView
    private lateinit var tvConvertedMoney : TextView
    private var selectedCurrency: String? = null

    private val accessKey = "3bb761af5597d06aa4310273f80f6d29"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtInput = findViewById(R.id.edtInput)
        spinner = findViewById(R.id.spnSelectCurrency)
        tvBaseCurrency = findViewById(R.id.tvBaseCurrency)
        tvShowError = findViewById(R.id.tvShowError)
        tvConvertedCurrency = findViewById(R.id.tvConvertedCurrency)
        tvConvertedMoney = findViewById(R.id.tvConvertedMoney)
        btnConvert = findViewById(R.id.btnConvert)

        // Get Base currency
        getBaseCurrency()

        // Get Rate list
        getRateList()

        // Event for Convert Button
        btnConvert.setOnClickListener {
            selectedCurrency?.let {
                handleConvertCurrency(it)
            }
        }
    }

    // Get Base Currency
    private fun getBaseCurrency() {
        RetrofitClient.apiService.getExchangeRates(accessKey).enqueue(object : Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                if (response.isSuccessful) {
                    val exchangeRates = response.body()
                    exchangeRates?.let {
                        tvBaseCurrency.text = it.base
                    }
                } else {
                    Log.e("ERROR", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                Log.e("API_ERROR", "Error occurred: " + t.message)
            }
        })
    }

    // Get Rate List
    private fun getRateList() {
        RetrofitClient.apiService.getExchangeRates(accessKey).enqueue(object : Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                if (response.isSuccessful) {
                    val rates = response.body()?.rates ?: emptyMap()
                    val adapter = CurrencySpinnerAdapter(this@MainActivity, rates)
                    spinner.adapter = adapter

                    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                            selectedCurrency = adapter.getItem(position).first
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            selectedCurrency = null
                        }
                    }
                } else {
                    Log.e("ERROR", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                Log.e("API_ERROR", "Error occurred: " + t.message)
            }
        })
    }

    // Handle Convert Currency
    private fun handleConvertCurrency(targetCurrency: String) {
        val message = "Please fill in an amount!"
        val amount = edtInput.text.toString()

        val amountValue = amount.toDoubleOrNull()

        if (amount.isEmpty() || amountValue == null) {
            tvShowError.visibility = View.VISIBLE
            tvShowError.text = message
        } else {
            tvShowError.visibility = View.GONE
            calculateExchangeRate(amountValue, targetCurrency)
        }
    }

    // Calculate Exchange Rate
    private fun calculateExchangeRate(amount: Double, targetCurrency: String) {
        RetrofitClient.apiService.getExchangeRates(accessKey).enqueue(object : Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                if (response.isSuccessful) {
                    val exchangeRates = response.body()
                    exchangeRates?.let {
                        val rate = it.rates[targetCurrency] ?: 1.0
                        val exchangeValue = amount * rate

                        tvConvertedCurrency.text = targetCurrency
                        tvConvertedMoney.text = exchangeValue.toString()
                    }
                } else {
                    Log.e("ERROR", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                Log.e("API_ERROR", "Error occurred: " + t.message)
            }
        })
    }
}