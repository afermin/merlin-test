package com.merlinjobs.currencyexchange.core.use_cases.exchange

import com.merlinjobs.currencyexchange.core.use_cases.base.CompletableUseCase
import com.merlinjobs.currencyexchange.data.repository.IExchangeRatesRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetExchangeRatesUseCase(mSubscribeOnScheduler: Scheduler,
                              mObserverOnScheduler: Scheduler) :
        CompletableUseCase<Pair<String, String>>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mExchangeRatesRepository: IExchangeRatesRepository

    override fun buildUseCase(params: Pair<String, String>): Completable {
        return Completable.fromObservable(mExchangeRatesRepository.getExchangeRates(params.first,
                params.second))
    }
}