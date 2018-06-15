package com.merlinjobs.currencyexchange.exchange

import android.widget.EditText
import com.merlinjobs.currencyexchange.IBaseView
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion

interface IExchangeActivityView : IBaseView {

    fun getValueEditText(): EditText?

    fun showConversions(conversions: List<ExchangeConversion>)

    fun clearConversions()

    fun showPreferenceDialog()
}



