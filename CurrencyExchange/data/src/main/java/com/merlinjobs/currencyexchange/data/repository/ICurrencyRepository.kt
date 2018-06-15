package com.merlinjobs.currencyexchange.data.repository

import android.content.Context
import com.merlinjobs.currencyexchange.data.local.models.Currency
import io.reactivex.Single

interface ICurrencyRepository {
    var mCurrencies: List<Currency>?

    fun getCurrencies(context: Context): Single<List<Currency>>
}