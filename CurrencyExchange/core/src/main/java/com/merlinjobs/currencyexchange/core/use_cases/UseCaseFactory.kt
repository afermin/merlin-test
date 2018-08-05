package com.merlinjobs.currencyexchange.core.use_cases

import android.arch.lifecycle.LiveData
import android.content.Context
import android.widget.EditText
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import com.merlinjobs.currencyexchange.core.di.DaggerRepositoryComponent
import com.merlinjobs.currencyexchange.core.di.RepositoryModule
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.IObservableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.core.use_cases.currency.GetCurrenciesUsecase
import com.merlinjobs.currencyexchange.core.use_cases.exchange.CalculateExchangeRateUseCase
import com.merlinjobs.currencyexchange.core.use_cases.exchange.GetExchangeRatesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.GetFavoriteCurrenciesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.GetHasWacthedPreferenceDialogUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.SaveFavoriteCurrenciesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.SaveHasWatchedFavoriteDialog
import com.merlinjobs.currencyexchange.core.use_cases.system.CreateLocalStorageUseCase
import com.merlinjobs.currencyexchange.core.use_cases.system.NewSubscribeToCurrencyConversionsUseCase
import com.merlinjobs.currencyexchange.core.use_cases.system.SubscribeEditTextChangesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.system.SubscribeToCurrencyConversionsUseCase
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UseCaseFactory : IUseCaseFactory {

    private val mRepostoryComponent by lazy {
        DaggerRepositoryComponent.builder()
                .repositoryModule(RepositoryModule())
                .build()
    }

    override val getCurrenciesUsecase: ISingleUseCase<List<Currency>, Context>
        get() {
            val iterator = GetCurrenciesUsecase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val getExchangeRateUseCase: ICompletableUseCase<Pair<String, String>>
        get() {
            val iterator = GetExchangeRatesUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val subscribeToEditTextChangesUseCase: IObservableUseCase<TextViewAfterTextChangeEvent, EditText>
        get() {
            val iterator = SubscribeEditTextChangesUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val subscribeToCurrencyConversionsUSeCase: IObservableUseCase<List<ExchangeConversion>, Any?>
        get() {
            val iterator = SubscribeToCurrencyConversionsUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }


    override val newSubscribeToCurrencyConversionsUSeCase: IObservableUseCase<LiveData<List<ExchangeConversion>>, Any?>
        get() {
            val iterator = NewSubscribeToCurrencyConversionsUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val calculateExchangeRateUseCase: ICompletableUseCase<Double>
        get() {
            val iterator = CalculateExchangeRateUseCase(Schedulers.io(),
                    AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val getFavoriteCurrenciesUSeCase: ISingleUseCase<List<String>, Any?>
        get() {
            val iterator = GetFavoriteCurrenciesUseCase(Schedulers.io(), AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val saveFavoriteCurrenciesUseCase: ICompletableUseCase<List<String>>
        get() {
            val iterator = SaveFavoriteCurrenciesUseCase(Schedulers.io(), AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val createLocalStorageUseCase: ICompletableUseCase<Context>
        get() {
            val iterator = CreateLocalStorageUseCase(Schedulers.io(), AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val getHasWacthedPreferenceDialogUseCase: ISingleUseCase<Boolean, Any?>
        get() {
            val iterator = GetHasWacthedPreferenceDialogUseCase(Schedulers.io(), AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }

    override val saveHAsWacthedFavoriteDialog: ICompletableUseCase<Boolean>
        get() {
            val iterator = SaveHasWatchedFavoriteDialog(Schedulers.io(), AndroidSchedulers.mainThread())
            mRepostoryComponent.inject(iterator)
            return iterator
        }
}