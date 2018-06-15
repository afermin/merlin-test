package com.merlinjobs.currencyexchange.core.use_cases.preferences

import com.merlinjobs.currencyexchange.core.use_cases.base.CompletableUseCase
import com.merlinjobs.currencyexchange.data.FAVORITE_CURRENCIES
import com.merlinjobs.currencyexchange.data.repository.IPreferencesRepository
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class SaveFavoriteCurrenciesUseCase(mSubcribeOnScheduler: Scheduler,
                                    mObserverOnScheduler: Scheduler) :
        CompletableUseCase<List<String>>(mSubcribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mPreferenceRepository: IPreferencesRepository

    override fun buildUseCase(params: List<String>): Completable {
        return Completable.create { emitter ->
            mPreferenceRepository.storeData(FAVORITE_CURRENCIES, Gson().toJson(params))
            emitter.onComplete()
        }
    }
}