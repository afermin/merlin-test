package com.merlinjobs.currencyexchange.core.use_cases.system

import android.content.Context
import com.merlinjobs.currencyexchange.core.use_cases.base.CompletableUseCase
import com.merlinjobs.currencyexchange.data.repository.IPreferencesRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import javax.inject.Inject

class CreateLocalStorageUseCase(mSubscribeOnScheduler: Scheduler,
                                mObserverOnScheduler: Scheduler) :
        CompletableUseCase<Context>(mSubscribeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mPreferenceRepository: IPreferencesRepository

    override fun buildUseCase(params: Context): Completable {
        return Completable.create { emitter ->
            mPreferenceRepository.createLocalStorage(params)
            emitter.onComplete()
        }
    }
}