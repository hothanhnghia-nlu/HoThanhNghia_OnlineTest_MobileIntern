package com.tyme.vietnam.currencyConverter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.tyme.vietnam.currencyConverter.R

class CurrencySpinnerAdapter (
    private val context: Context,
    private val rates: Map<String, Double>
) : BaseAdapter() {
    private val currencyList = rates.toList()

    override fun getCount(): Int = currencyList.size

    override fun getItem(position: Int): Pair<String, Double> = currencyList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_rate_item, parent, false)

        val currencyCodeTextView: TextView = view.findViewById(R.id.tvCurrencyCode)
        val exchangeRateTextView: TextView = view.findViewById(R.id.tvExchangeRate)

        val (currencyCode, exchangeRate) = getItem(position)

        currencyCodeTextView.text = currencyCode
        exchangeRateTextView.text = exchangeRate.toString()

        return view
    }
}