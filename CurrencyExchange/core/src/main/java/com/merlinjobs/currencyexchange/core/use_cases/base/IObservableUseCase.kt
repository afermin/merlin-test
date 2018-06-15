package com.merlinjobs.currencyexchange.core.use_cases.base

import io.reactivex.observers.DisposableObserver

interface IObservableUseCase<Response, Params>{

    fun execute(params: Params, observer: DisposableObserver<Response>)

    fun dispose()
}