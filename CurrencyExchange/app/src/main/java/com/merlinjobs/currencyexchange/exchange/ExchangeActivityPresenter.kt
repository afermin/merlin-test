package com.merlinjobs.currencyexchange.exchange

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.widget.EditText
import com.merlinjobs.currencyexchange.BaseApplication
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.IObservableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject


class ExchangeActivityPresenter : IExchangeActivityPresenter {

    override var mView: IExchangeActivityView? = null

    @Inject
    lateinit var mSubscribeEditTextToChangeUseCase: IObservableUseCase<TextViewAfterTextChangeEvent, EditText>

    @Inject
    lateinit var mSubscribeToExchangeConversionsUseCase: IObservableUseCase<List<ExchangeConversion>, Any?>

    @Inject
    lateinit var mCalculateExchangeRateUseCase: ICompletableUseCase<Double>

    @Inject
    lateinit var mGetHAsWatchedPreferenceDialogUseCase: ISingleUseCase<Boolean, Any?>

    @Inject
    lateinit var mSaveHasWatchedFavoriteDialog: ICompletableUseCase<Boolean>

    private val mDisposableBag = CompositeDisposable()


    init {
        BaseApplication.getInstance()
                .useCaseComponent.inject(this)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun checkIfHasTOPresentFavoriteCurrencies() {
        val disposable = object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                if (!t) {
                    mView?.showPreferenceDialog()
                    saveHasWatchedPreferenceDialog()
                }
            }

            override fun onError(e: Throwable) {
            }
        }
        mDisposableBag.add(disposable)
        mGetHAsWatchedPreferenceDialogUseCase.execute(null, disposable)

    }


    private fun isNumeric(str: String): Boolean {
        try {
            str.toDouble()
        } catch (e: NumberFormatException) {
            return false
        }

        return true
    }

    private fun saveHasWatchedPreferenceDialog() {
        val disposable = object : DisposableCompletableObserver() {
            override fun onComplete() {
                mDisposableBag.remove(this)

            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mDisposableBag.remove(this)
            }
        }
        mDisposableBag.add(disposable)
        mSaveHasWatchedFavoriteDialog.execute(true, disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun subscribeToExchangeConversions() {
        val disposable = object : DisposableObserver<List<ExchangeConversion>>() {
            override fun onComplete() {

            }

            override fun onNext(t: List<ExchangeConversion>) {
                mView?.showConversions(t)

            }

            override fun onError(e: Throwable) {
                mDisposableBag.remove(this)
            }
        }
        mDisposableBag.add(disposable)
        mSubscribeToExchangeConversionsUseCase.execute(null, disposable)
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun subscribeToEditTextChangeValues() {
        mView?.getValueEditText()?.let {
            val disposable = object :
                    DisposableObserver<TextViewAfterTextChangeEvent>() {
                override fun onComplete() {

                }

                override fun onNext(t: TextViewAfterTextChangeEvent) {
                    t.editable()?.let {
                        if (isNumeric(it.toString())) {
                            calculateExchange(it.toString().toDouble())
                        } else {
                            mView?.clearConversions()
                        }
                    }

                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    mDisposableBag.remove(this)
                }
            }
            mDisposableBag.add(disposable)
            mSubscribeEditTextToChangeUseCase.execute(it, disposable)
        }
    }

    private fun calculateExchange(value: Double) {
        val disposable = object : DisposableCompletableObserver() {
            override fun onComplete() {
                mDisposableBag.remove(this)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mDisposableBag.remove(this)
            }
        }

        mDisposableBag.add(disposable)
        mCalculateExchangeRateUseCase.execute(value, disposable)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposableBag.clear()
    }
}