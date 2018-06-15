package com.merlinjobs.currencyexchange.data.repository

import com.merlinjobs.currencyexchange.data.network.models.APICurrencyResponse
import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface IExchangeRatesRepository {

    fun getExchangeRates(base: String, symbols: String): Observable<APICurrencyResponse>

    val mExchangePublisher: Subject<HashMap<String, Double>>

    fun calculateExchangeRate(quantity: Double)

    fun doneWithConversions()
}