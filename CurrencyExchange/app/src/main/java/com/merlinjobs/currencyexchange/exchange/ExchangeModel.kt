package com.merlinjobs.currencyexchange.exchange

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.IObservableUseCase
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver

/**
 * Created by Alexander Fermin (alexfer06@gmail.com) on 04/08/2018.
 */
class ExchangeModel(private val mSubscribeToExchangeConversionsUseCase: IObservableUseCase<List<ExchangeConversion>, Any?>,
                    private val mCalculateExchangeRateUseCase: ICompletableUseCase<Double>) {


    fun getExchangesConversion(): LiveData<List<ExchangeConversion>> {

        val data = MutableLiveData<List<ExchangeConversion>>()
        val disposable = object : DisposableObserver<List<ExchangeConversion>>() {
            override fun onComplete() {}

            override fun onNext(t: List<ExchangeConversion>) {
                data.value = t
            }

            override fun onError(e: Throwable) {

            }
        }

        mSubscribeToExchangeConversionsUseCase.execute(null, disposable)
        return data
    }

    fun calculateExchange(value: Double) {
            val disposable = object : DisposableCompletableObserver() {
                override fun onComplete() {
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            }
            mCalculateExchangeRateUseCase.execute(value, disposable)
    }


}