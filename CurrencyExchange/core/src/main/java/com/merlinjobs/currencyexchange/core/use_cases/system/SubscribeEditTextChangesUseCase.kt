package com.merlinjobs.currencyexchange.core.use_cases.system

import android.widget.EditText
import com.merlinjobs.currencyexchange.core.use_cases.base.ObservableUseCase
import com.jakewharton.rxbinding2.widget.RxTextView
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import io.reactivex.Observable
import io.reactivex.Scheduler

class SubscribeEditTextChangesUseCase(mSubscribeonScheduler: Scheduler,
                                      mObserverOnScheduler: Scheduler) :
        ObservableUseCase<TextViewAfterTextChangeEvent, EditText>(mSubscribeonScheduler, mObserverOnScheduler) {

    override fun buildUseCase(params: EditText): Observable<TextViewAfterTextChangeEvent> {
        return RxTextView.afterTextChangeEvents(params)

    }
}