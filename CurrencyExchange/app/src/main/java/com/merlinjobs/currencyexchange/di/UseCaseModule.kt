package com.merlinjobs.currencyexchange.di

import android.arch.lifecycle.LiveData
import android.content.Context
import android.widget.EditText
import com.merlinjobs.currencyexchange.core.use_cases.IUseCaseFactory
import com.merlinjobs.currencyexchange.core.use_cases.UseCaseFactory
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.IObservableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import com.merlinjobs.currencyexchange.exchange.ExchangeModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun provideUseCaseFactory(): IUseCaseFactory {
        return UseCaseFactory()
    }

    @Provides
    @Singleton
    fun providesGetCurrenciesUseCase(useCaseFactory: IUseCaseFactory): ISingleUseCase<List<Currency>, Context> {
        return useCaseFactory.getCurrenciesUsecase
    }

    @Provides
    @Singleton
    fun providesGetExchangeRatesUSeCase(useCaseFactory: IUseCaseFactory): ICompletableUseCase<Pair<String, String>> {
        return useCaseFactory.getExchangeRateUseCase
    }

    @Provides
    @Singleton
    fun providesSubscribeEditTextChangeUseCase(useCaseFactory: IUseCaseFactory):
            IObservableUseCase<TextViewAfterTextChangeEvent, EditText> {
        return useCaseFactory.subscribeToEditTextChangesUseCase
    }

    @Provides
    @Singleton
    fun providesSubscribeToCurrencyConversionUseCase(useCaseFactory: IUseCaseFactory):
            IObservableUseCase<List<ExchangeConversion>, Any?> {
        return useCaseFactory.subscribeToCurrencyConversionsUSeCase
    }

    @Provides
    @Singleton
    fun providesNewSubscribeToCurrencyConversionUseCase(useCaseFactory: IUseCaseFactory):
            IObservableUseCase<LiveData<List<ExchangeConversion>>, Any?> {
        return useCaseFactory.newSubscribeToCurrencyConversionsUSeCase
    }

    @Provides
    @Singleton
    fun providesCalculateExchangeRateUseCase(useCaseFactory: IUseCaseFactory): ICompletableUseCase<Double> {
        return useCaseFactory.calculateExchangeRateUseCase
    }

    @Provides
    @Singleton
    fun providesCreateLocalStorageUseCase(useCaseFactory: IUseCaseFactory): ICompletableUseCase<Context> {
        return useCaseFactory.createLocalStorageUseCase
    }

    @Provides
    @Singleton
    fun providesGetFavoriteCurrenciesUseCase(useCaseFactory: IUseCaseFactory): ISingleUseCase<List<String>, Any?> {
        return useCaseFactory.getFavoriteCurrenciesUSeCase
    }

    @Provides
    @Singleton
    fun providesSaveFavoriteCurrenciesUseCase(useCaseFactory: IUseCaseFactory): ICompletableUseCase<List<String>> {
        return useCaseFactory.saveFavoriteCurrenciesUseCase
    }


    @Provides
    @Singleton
    fun providesGetHAsWatchedPreferenceDialog(useCaseFactory: IUseCaseFactory): ISingleUseCase<Boolean, Any?> {
        return useCaseFactory.getHasWacthedPreferenceDialogUseCase
    }

    @Provides
    @Singleton
    fun providesSaveHasWatchedPreferenceDialog(useCaseFactory: IUseCaseFactory): ICompletableUseCase<Boolean> {
        return useCaseFactory.saveHAsWacthedFavoriteDialog
    }

    @Provides
    fun providesExchangeModel(exchangeConversionsUseCase : IObservableUseCase<List<ExchangeConversion>, Any?>,
            calculateExchangeRateUseCase: ICompletableUseCase<Double>): ExchangeModel {
        return ExchangeModel(exchangeConversionsUseCase, calculateExchangeRateUseCase)
    }

}