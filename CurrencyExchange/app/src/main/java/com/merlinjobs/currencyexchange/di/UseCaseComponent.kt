package com.merlinjobs.currencyexchange.di

import com.merlinjobs.currencyexchange.exchange.ExchangeActivityViewModel
import com.merlinjobs.currencyexchange.preferences.PreferenceFragmentDialogPresenter
import com.merlinjobs.currencyexchange.splash.SplashScreenPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(UseCaseModule::class)])
interface UseCaseComponent {

    fun inject(splashScreenPresenter: SplashScreenPresenter)

    fun inject(exchangeActivityViewModel: ExchangeActivityViewModel)

    fun inject(preferenceDialogPresenter: PreferenceFragmentDialogPresenter)

}