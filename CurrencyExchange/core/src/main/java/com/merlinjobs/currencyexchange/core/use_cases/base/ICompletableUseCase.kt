package com.merlinjobs.currencyexchange.core.use_cases.base

import io.reactivex.Completable
import io.reactivex.observers.DisposableCompletableObserver

interface ICompletableUseCase<Params> {


    fun getUseCase(params: Params): Completable

    fun execute(params: Params, observer: DisposableCompletableObserver)

    fun dispose()
}