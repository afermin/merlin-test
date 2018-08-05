package com.merlinjobs.currencyexchange.core.use_cases

import android.arch.lifecycle.LiveData
import android.content.Context
import android.widget.EditText
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.IObservableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent

interface IUseCaseFactory {
    val getCurrenciesUsecase: ISingleUseCase<List<Currency>, Context>
    val getExchangeRateUseCase: ICompletableUseCase<Pair<String, String>>
    val subscribeToEditTextChangesUseCase: IObservableUseCase<TextViewAfterTextChangeEvent, EditText>
    val subscribeToCurrencyConversionsUSeCase: IObservableUseCase<List<ExchangeConversion>, Any?>
    val newSubscribeToCurrencyConversionsUSeCase: IObservableUseCase<LiveData<List<ExchangeConversion>>, Any?>
    val calculateExchangeRateUseCase: ICompletableUseCase<Double>
    val createLocalStorageUseCase: ICompletableUseCase<Context>
    val getFavoriteCurrenciesUSeCase: ISingleUseCase<List<String>, Any?>
    val saveFavoriteCurrenciesUseCase: ICompletableUseCase<List<String>>
    val getHasWacthedPreferenceDialogUseCase: ISingleUseCase<Boolean, Any?>
    val saveHAsWacthedFavoriteDialog: ICompletableUseCase<Boolean>
}