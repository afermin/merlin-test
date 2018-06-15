package com.merlinjobs.currencyexchange.preferences

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.merlinjobs.currencyexchange.BaseApplication
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.data.DEFAULT_FAVORITE_CURRENCIES
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class PreferenceFragmentDialogPresenter : IPreferenceFragmentDialogPresenter {
    override var mView: IPreferenceFragmentDialogView? = null


    @Inject
    lateinit var mGetFavoriteCurrenciesUseCase: ISingleUseCase<List<String>, Any?>

    @Inject
    lateinit var mSaveFavoritesCurrenciesUseCase: ICompletableUseCase<List<String>>

    @Inject
    lateinit var mGetExchangeRateUseCase: ICompletableUseCase<Pair<String, String>>

    private val mDefaultFavoriteCurrencyList: List<String> =
            Gson().fromJson(DEFAULT_FAVORITE_CURRENCIES, object : TypeToken<List<String>>() {}.type)

    private val mDisposableBag = CompositeDisposable()

    init {
        BaseApplication.getInstance()
                .useCaseComponent.inject(this)
    }


    override fun onDonePressed() {
        mView?.showProgressBar()
        val favoriteCurrencies = mView?.getFavoriteCurrencies()
        favoriteCurrencies?.let {
            val disposable = object:DisposableCompletableObserver(){
                override fun onComplete() {
                    mView?.hideProgressBar()
                    refreshExchangeRatesUseCase(favoriteCurrencies)
                    mDisposableBag.remove(this)
                }

                override fun onError(e: Throwable) {
                    mView?.hideProgressBar()
                    mDisposableBag.remove(this)
                    e.printStackTrace()
                }
            }
            mDisposableBag.add(disposable)
            mSaveFavoritesCurrenciesUseCase.execute(favoriteCurrencies, disposable)
        }
    }

    private fun refreshExchangeRatesUseCase(currencies:List<String>){
        mView?.showProgressBar()
        val disposable = object : DisposableCompletableObserver() {
            override fun onComplete() {

                mView?.updateDone()
                mDisposableBag.remove(this)
                mView?.hideProgressBar()
            }

            override fun onError(e: Throwable) {
                mDisposableBag.remove(this)
                e.printStackTrace()
                mView?.hideProgressBar()
            }

        }
        mDisposableBag.add(disposable)
        mGetExchangeRateUseCase.execute(Pair("USD", Gson().toJson(currencies)), disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun getFavoritesCurrencies() {
        mView?.showProgressBar()
        val disposable = object : DisposableSingleObserver<List<String>>() {
            override fun onSuccess(t: List<String>) {
                mView?.showFavoritesCurrencies(BaseApplication.getInstance()
                        .getmCurrencies(), ArrayList(t))
                mDisposableBag.remove(this)
                mView?.hideProgressBar()
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                mDisposableBag.remove(this)
                mView?.hideProgressBar()
            }
        }

        mDisposableBag.add(disposable)
        mGetFavoriteCurrenciesUseCase.execute(null, disposable)

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun stop() {
        mDisposableBag.clear()
    }

}