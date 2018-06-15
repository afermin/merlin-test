package com.merlinjobs.currencyexchange.core.di

import com.merlinjobs.currencyexchange.core.use_cases.currency.GetCurrenciesUsecase
import com.merlinjobs.currencyexchange.core.use_cases.system.SubscribeEditTextChangesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.system.SubscribeToCurrencyConversionsUseCase
import com.merlinjobs.currencyexchange.core.use_cases.exchange.CalculateExchangeRateUseCase
import com.merlinjobs.currencyexchange.core.use_cases.exchange.GetExchangeRatesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.GetFavoriteCurrenciesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.GetHasWacthedPreferenceDialogUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.SaveFavoriteCurrenciesUseCase
import com.merlinjobs.currencyexchange.core.use_cases.preferences.SaveHasWatchedFavoriteDialog
import com.merlinjobs.currencyexchange.core.use_cases.system.CreateLocalStorageUseCase
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(RepositoryModule::class)])
interface RepositoryComponent {

    fun inject(getCurrenciesUseCase: GetCurrenciesUsecase)

    fun inject(getExchangeRatesUseCase: GetExchangeRatesUseCase)

    fun inject(subscribeEditTextChangesUseCase: SubscribeEditTextChangesUseCase)

    fun inject(subscribeToCurrencyConversionsUseCase: SubscribeToCurrencyConversionsUseCase)

    fun inject(calculateExchangeRateUseCase: CalculateExchangeRateUseCase)

    fun inject(getFavoriteCurrenciesUseCase: GetFavoriteCurrenciesUseCase)

    fun inject(saveFavoriteCurrenciesUseCase: SaveFavoriteCurrenciesUseCase)

    fun inject(createLocalStorageUseCase: CreateLocalStorageUseCase)

    fun inject(getHasWacthedPreferenceDialogUseCase: GetHasWacthedPreferenceDialogUseCase)

    fun inject(saveHasWatchedFavoriteDialog: SaveHasWatchedFavoriteDialog)
}