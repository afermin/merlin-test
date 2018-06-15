package com.merlinjobs.currencyexchange.data.di

import com.merlinjobs.currencyexchange.data.repository.ExchangeRatesRepository

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(RouteModule::class)])
interface RoutesComponent {

    fun inject(exchangeRatesRepository: ExchangeRatesRepository)
}