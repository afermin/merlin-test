package com.merlinjobs.currencyexchange.exchange

import android.arch.lifecycle.*
import android.databinding.Observable
import android.databinding.PropertyChangeRegistry
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.merlinjobs.currencyexchange.BaseApplication
import com.merlinjobs.currencyexchange.core.use_cases.base.ICompletableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.IObservableUseCase
import com.merlinjobs.currencyexchange.core.use_cases.base.ISingleUseCase
import com.merlinjobs.currencyexchange.core.utils.SingleLiveEvent
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import org.apache.commons.lang3.StringUtils
import javax.inject.Inject


/**
 * Created by Alexander Fermin (alexfer06@gmail.com) on 02/08/2018.
 */

class ExchangeActivityViewModel : ViewModel(), Observable, LifecycleObserver {

    @Inject
    lateinit var mExchangeModel: ExchangeModel

    @Inject
    lateinit var mSubscribeToExchangeConversionsUseCase: IObservableUseCase<List<ExchangeConversion>, Any?>

    @Inject
    lateinit var mCalculateExchangeRateUseCase: ICompletableUseCase<Double>

    @Inject
    lateinit var mGetHAsWatchedPreferenceDialogUseCase: ISingleUseCase<Boolean, Any?>

    @Inject
    lateinit var mSaveHasWatchedFavoriteDialog: ICompletableUseCase<Boolean>

    private var conversions: LiveData<List<ExchangeConversion>> = MutableLiveData()

    private val mDisposableBag = CompositeDisposable()

    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    init {
        BaseApplication.getInstance()
                .useCaseComponent.inject(this)
    }

    private val actions = SingleLiveEvent<Action>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun checkIfHasTOPresentFavoriteCurrencies() {
        val disposable = object : DisposableSingleObserver<Boolean>() {
            override fun onSuccess(t: Boolean) {
                if (!t) {
                    actions.value = Action.OPEN_DIALOG_PREFERENCES
                    saveHasWatchedPreferenceDialog()
                }
            }

            override fun onError(e: Throwable) {
            }
        }
        mDisposableBag.add(disposable)
        mGetHAsWatchedPreferenceDialogUseCase.execute(null, disposable)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    private fun subscribeToExchangeConversions() {
        Log.d("TAG", "ON_CREATE")
        conversions = mExchangeModel.getExchangesConversion()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private fun onStop() {
        mDisposableBag.clear()
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

    fun getValueTextWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable) {
                Log.d("TAG", s.toString())
                if (StringUtils.isNumeric(s.toString())) {
                    mExchangeModel.calculateExchange(s.toString().toDouble())
                } else {
                    actions.value = Action.CLEAR_CONVERSIONS
                }
            }
        }
    }

    fun getConversions(): LiveData<List<ExchangeConversion>> {
        return conversions
    }

    fun getActions(): LiveData<Action> {
        return actions
    }

    override fun addOnPropertyChangedCallback(
            callback: Observable.OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
            callback: Observable.OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    enum class Action {
        OPEN_DIALOG_PREFERENCES,
        CLEAR_CONVERSIONS
    }

}

