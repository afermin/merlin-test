package com.merlinjobs.currencyexchange.exchange

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.merlinjobs.currencyexchange.R

class ExchangeViewHolder(mItemView: View) : RecyclerView.ViewHolder(mItemView) {


    val mTVCurrencySymbol = mItemView.findViewById<TextView>(R.id.mTVCurrencySymbol)
    val mTVCurrencyAbbreviation = mItemView.findViewById<TextView>(R.id.mTVCurrencyAbbreviation)
    val mTVCurrencyName = mItemView.findViewById<TextView>(R.id.mTVCurrencyName)
    val mTVValue = mItemView.findViewById<TextView>(R.id.mTVValue)
}