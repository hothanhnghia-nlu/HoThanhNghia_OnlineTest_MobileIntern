package com.tyme.vietnam.currencyConverter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.tyme.vietnam.currencyConverter.api.RetrofitClient
import com.tyme.vietnam.currencyConverter.model.ExchangeRates

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var edtInput : EditText
    private lateinit var btnConvert : Button
    private lateinit var showError : TextView
    private lateinit var originalMoney : TextView
    private lateinit var convertedMoney : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtInput = findViewById(R.id.edtInput)
        btnConvert = findViewById(R.id.btnConvert)
        showError = findViewById(R.id.showError)
        originalMoney = findViewById(R.id.originalMoney)
        convertedMoney = findViewById(R.id.convertedMoney)

        // Event for Convert Button
        btnConvert.setOnClickListener {
            if (checkNetworkPermission(this)) {
                fetchExchangeRates()
            } else {
                Toast.makeText(this, "Please check network connection...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Handle Convert Currency
    private fun handleConvertCurrency() {
        val message = "Please Fill an amount!"
        val amount = edtInput.text.toString()

        val amountValue = amount.toDoubleOrNull()

        if (amount.isEmpty() || amountValue == null) {
            showError.visibility = View.VISIBLE
            showError.text = message
        } else {
            showError.visibility = View.GONE

            this.originalMoney.text = amountValue.toString()
        }
    }

    private fun fetchExchangeRates() {
        val accessKey = "3bb761af5597d06aa4310273f80f6d29"

        RetrofitClient.apiService.getLatestExchangeRates(accessKey).enqueue(object : Callback<ExchangeRates> {
            override fun onResponse(call: Call<ExchangeRates>, response: Response<ExchangeRates>) {
                if (response.isSuccessful) {
                    val exchangeRates = response.body()
                    // Xử lý dữ liệu ở đây
                    exchangeRates?.let {
                        println("Base Currency: ${it.base}")
                        println("Date: ${it.date}")
                        println("Rates: ${it.rates}")
                        // Ví dụ: In tỷ giá USD
                        println("USD Rate: ${it.rates["USD"]}")
                    }
                } else {
                    // Xử lý lỗi
                    println("Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ExchangeRates>, t: Throwable) {
                // Xử lý lỗi kết nối
                t.printStackTrace()
            }
        })
    }

    // Check network permission
    private fun checkNetworkPermission(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)

        return networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }

}