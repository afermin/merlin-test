package com.merlinjobs.currencyexchange.preferences

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.merlinjobs.currencyexchange.R

class PreferenceCurrencyViewHolder(mItemView:View):RecyclerView.ViewHolder(mItemView){


    val mTVCurrencySymbol = mItemView.findViewById<TextView>(R.id.mTVCurrencySymbol)
    val mTVCurrencyName  = mItemView.findViewById<TextView>(R.id.mTVCurrencyName)


}