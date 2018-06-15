package com.merlinjobs.currencyexchange

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

interface IBasePresenter<T : IBaseView> : LifecycleObserver {
    var mView: T?

    fun bind(view: T) {
        mView = view
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unBind() {
        mView = null
    }

}