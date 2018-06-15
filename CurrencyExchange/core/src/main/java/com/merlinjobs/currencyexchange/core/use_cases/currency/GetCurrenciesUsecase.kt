package com.merlinjobs.currencyexchange.core.use_cases.currency

import android.content.Context
import com.merlinjobs.currencyexchange.core.use_cases.base.SingleUseCase
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.merlinjobs.currencyexchange.data.repository.ICurrencyRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject


class GetCurrenciesUsecase(mSubscribeOnScheduler: Scheduler,
                           mObserverOnScheduler: Scheduler) :
        SingleUseCase<List<Currency>, Context>(mObserverOnScheduler, mSubscribeOnScheduler) {

    @Inject
    lateinit var mCurrencyRepository: ICurrencyRepository

    override fun buildUseCase(params: Context): Single<List<Currency>> {
        return mCurrencyRepository.getCurrencies(params)
    }
}