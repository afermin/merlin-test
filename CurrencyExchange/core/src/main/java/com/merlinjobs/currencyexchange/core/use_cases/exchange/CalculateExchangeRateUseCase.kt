package com.merlinjobs.currencyexchange.core.use_cases.exchange

import com.merlinjobs.currencyexchange.core.use_cases.base.CompletableUseCase
import com.merlinjobs.currencyexchange.data.repository.IExchangeRatesRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class CalculateExchangeRateUseCase(mSubcribeOnScheduler: Scheduler,
                                   mObserveOnScheduler: Scheduler) :
        CompletableUseCase<Double>(mSubcribeOnScheduler, mObserveOnScheduler) {


    @Inject
    lateinit var mExchangeRatesRepository: IExchangeRatesRepository

    override fun buildUseCase(params: Double): Completable {
        return Completable.create {
            mExchangeRatesRepository.calculateExchangeRate(params)
            it.onComplete()
        }
    }
}