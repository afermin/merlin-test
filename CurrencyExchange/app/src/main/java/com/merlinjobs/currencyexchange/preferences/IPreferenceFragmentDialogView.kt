package com.merlinjobs.currencyexchange.preferences

import com.merlinjobs.currencyexchange.IBaseView
import com.merlinjobs.currencyexchange.data.local.models.Currency

interface IPreferenceFragmentDialogView : IBaseView {

    fun showFavoritesCurrencies(currencies: List<Currency>, favoriteCurrencies: ArrayList<String>)

    fun getFavoriteCurrencies(): List<String>?

    fun updateDone()
}