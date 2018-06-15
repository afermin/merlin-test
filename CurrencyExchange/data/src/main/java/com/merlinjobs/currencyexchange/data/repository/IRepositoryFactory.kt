package com.merlinjobs.currencyexchange.data.repository

interface IRepositoryFactory {

    fun getExchangeRepository(): IExchangeRatesRepository

    fun getCurrencyRepository(): ICurrencyRepository

    fun getPreferenceRepository(): IPreferencesRepository
}