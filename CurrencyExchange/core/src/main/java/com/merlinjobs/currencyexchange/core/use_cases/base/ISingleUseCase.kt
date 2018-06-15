package com.merlinjobs.currencyexchange.core.use_cases.base

import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

interface ISingleUseCase<Response, Params>{

    fun getUseCase(params: Params): Single<Response>

    fun execute(params: Params, observer: DisposableSingleObserver<Response>)

    fun dispose()
}