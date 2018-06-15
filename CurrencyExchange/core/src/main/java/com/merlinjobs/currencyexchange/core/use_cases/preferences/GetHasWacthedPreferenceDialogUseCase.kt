package com.merlinjobs.currencyexchange.core.use_cases.preferences

import com.merlinjobs.currencyexchange.core.use_cases.base.SingleUseCase
import com.merlinjobs.currencyexchange.data.HAS_WATCHED_FAVORITE_CURRENCIES_DIALOG
import com.merlinjobs.currencyexchange.data.repository.IPreferencesRepository
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetHasWacthedPreferenceDialogUseCase(mSubcribeOnScheduler: Scheduler,
                                           mObserveOnScheduler: Scheduler) :
        SingleUseCase<Boolean, Any?>(mSubcribeOnScheduler, mObserveOnScheduler) {

    @Inject
    lateinit var mPreferenceRepository: IPreferencesRepository

    override fun buildUseCase(params: Any?): Single<Boolean> {
        return Single.create { emitter ->
            val hasWathedFavoriteDialog = mPreferenceRepository.getData(HAS_WATCHED_FAVORITE_CURRENCIES_DIALOG)
                    ?: "0"

            emitter.onSuccess(hasWathedFavoriteDialog == "1")


        }
    }
}