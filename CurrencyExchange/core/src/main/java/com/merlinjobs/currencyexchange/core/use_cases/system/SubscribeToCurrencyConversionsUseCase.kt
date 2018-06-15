package com.merlinjobs.currencyexchange.core.use_cases.system

import com.merlinjobs.currencyexchange.core.use_cases.base.ObservableUseCase
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.merlinjobs.currencyexchange.data.repository.IExchangeRatesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject


class SubscribeToCurrencyConversionsUseCase(mSubcribeOnScheduler: Scheduler,
                                            mObserverOnScheduler: Scheduler) :
        ObservableUseCase<List<ExchangeConversion>, Any?>(mSubcribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mExchangeRatesRepository: IExchangeRatesRepository

    override fun buildUseCase(params: Any?): Observable<List<ExchangeConversion>> {
        return mExchangeRatesRepository.mExchangePublisher
                .map {
                    val resultList = ArrayList<ExchangeConversion>()
                    for ((entry, value) in it) {
                        resultList.add(ExchangeConversion(entry, value))
                    }
                    resultList
                }
    }
}