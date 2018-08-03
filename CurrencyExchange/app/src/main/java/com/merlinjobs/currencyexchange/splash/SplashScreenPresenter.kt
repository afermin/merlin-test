package com.merlinjobs.currencyexchange.splash

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import com.merlinjobs.currencyexchange.BaseApplication
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.data.CURRENCY
import com.merlinjobs.currencyexchange.data.local.models.Currency
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject

class SplashScreenPresenter : ISplashScreenPresenter {

    @Inject
    lateinit var mGetCurrenciesUseCase: ISingleUseCase<List<Currency>, Context>

    @Inject
    lateinit var mGetExchangeRateUseCase: ICompletableUseCase<Pair<String, String>>

    @Inject
    lateinit var mCreateLocalStorageUseCase: ICompletableUseCase<Context>

    @Inject
    lateinit var mGetFavoriteCurrenciesUseCase: ISingleUseCase<List<String>, Any?>

    override var mView: ISplashScreenView? = null

    private val mDisposableBag = CompositeDisposable()

    init {
        BaseApplication.getInstance()
                .useCaseComponent.inject(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun loadData() {
        mView?.let {
            val disposable = object : DisposableSingleObserver<List<Currency>>() {
                override fun onSuccess(t: List<Currency>) {
                    BaseApplication.getInstance().setmCurrencies(t)
                    createLocalStorage()
                    mDisposableBag.remove(this)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    mDisposableBag.remove(this)
                }
            }
            mDisposableBag.add(disposable)
            mGetCurrenciesUseCase.execute(it.getContext(), disposable)
        }
    }

    private fun createLocalStorage() {
        mView?.let {
            val disposable = object : DisposableCompletableObserver() {
                override fun onComplete() {
                    getFavoriteCurrencies()
                    mDisposableBag.remove(this)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                    mDisposableBag.remove(this)
                }
            }

            mDisposableBag.add(disposable)
            mCreateLocalStorageUseCase.execute(it.getContext(), disposable)
        }

    }

    private fun getFavoriteCurrencies() {
        val disposable = object : DisposableSingleObserver<List<String>>() {
            override fun onSuccess(t: List<String>) {
                getExchangeRates(t)
                mDisposableBag.remove(this)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mDisposableBag.remove(this)
            }
        }
        mDisposableBag.add(disposable)
        mGetFavoriteCurrenciesUseCase.execute(null, disposable)
    }

    private fun getExchangeRates(currencies: List<String>) {
        val disposable = object : DisposableCompletableObserver() {
            override fun onComplete() {
                mView?.navigateToNextActivity()
                mDisposableBag.remove(this)
            }

            override fun onError(e: Throwable) {
                mDisposableBag.remove(this)
                e.printStackTrace()
            }
        }

        mDisposableBag.add(disposable)
        mGetExchangeRateUseCase.execute(Pair(CURRENCY, StringUtils.join(currencies, ',')), disposable)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposableBag.clear()
    }
}


