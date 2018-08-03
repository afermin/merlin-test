package com.merlinjobs.currencyexchange.data.repository

import com.merlinjobs.currencyexchange.data.network.models.APICurrencyResponse
import com.merlinjobs.currencyexchange.data.network.routes.IConvertRoute
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import javax.inject.Inject

class ExchangeRatesRepository : IExchangeRatesRepository {

    @Inject
    lateinit var mConvertRoute: IConvertRoute

    override val mExchangePublisher: Subject<HashMap<String, Double>> = PublishSubject.create()

    private var mCurrentExchangeRate: HashMap<String, Double>? = null

    private var mDisposableBag = CompositeDisposable()

    override fun getExchangeRates(base: String, symbols: String): Observable<APICurrencyResponse> {
        return mConvertRoute.getCurrencyConversion(base, symbols)
                .doOnNext {
                    mCurrentExchangeRate = it.rates
                }
    }


    override fun calculateExchangeRate(quantity: Double) {
        mCurrentExchangeRate?.let {
            mDisposableBag.add(Single.create<HashMap<String, Double>> { emitter ->

                val result = HashMap<String, Double>()
                for ((key, value) in it) {
                    result[key] = quantity * value
                }

                emitter.onSuccess(result)
            }.subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { response ->
                        mExchangePublisher.onNext(response)
                    })

        }
    }

    override fun doneWithConversions() {
        mDisposableBag.clear()
    }


}