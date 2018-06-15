package com.merlinjobs.currencyexchange.core.use_cases.base

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<Response, Params>(private val mSubscribeOnScheduler: Scheduler,
                                               private val mObserverOnScheduler: Scheduler) :
        ISingleUseCase<Response, Params> {

    private val mCompositeDisposables = CompositeDisposable()

    abstract fun buildUseCase(params: Params): Single<Response>

    override fun execute(params: Params, observer: DisposableSingleObserver<Response>) {
        val observable = buildUseCase(params)
                .subscribeOn(mSubscribeOnScheduler)
                .observeOn(mObserverOnScheduler)

        mCompositeDisposables.add(observable.subscribeWith(observer))
    }

    override fun dispose() {
        mCompositeDisposables.dispose()
        mCompositeDisposables.clear()
    }

    override fun getUseCase(params: Params): Single<Response> {
        return buildUseCase(params)
    }


}