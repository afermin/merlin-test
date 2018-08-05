package com.merlinjobs.currencyexchange.core.use_cases.system

/**
 * Created by Alexander Fermin (alexfer06@gmail.com) on 02/08/2018.
 */


import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.merlinjobs.currencyexchange.core.use_cases.base.ObservableUseCase
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.merlinjobs.currencyexchange.data.repository.IExchangeRatesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject


class NewSubscribeToCurrencyConversionsUseCase(mSubscribeOnScheduler: Scheduler,
                                            mObserverOnScheduler: Scheduler) :
        ObservableUseCase<LiveData<List<ExchangeConversion>>, Any?>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mExchangeRatesRepository: IExchangeRatesRepository

    override fun buildUseCase(params: Any?): Observable<LiveData<List<ExchangeConversion>>> {
        return mExchangeRatesRepository.mExchangePublisher
                .map {

                    val data: MutableLiveData<List<ExchangeConversion>> = MutableLiveData()
                    val resultList = ArrayList<ExchangeConversion>()
                    for ((entry, value) in it) {
                        resultList.add(ExchangeConversion(entry, value))
                    }
                    data.value = resultList
                    data
                }
    }
}