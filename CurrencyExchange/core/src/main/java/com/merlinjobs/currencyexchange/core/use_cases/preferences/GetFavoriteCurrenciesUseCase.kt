package com.merlinjobs.currencyexchange.core.use_cases.preferences

import com.merlinjobs.currencyexchange.core.use_cases.base.SingleUseCase
import com.merlinjobs.currencyexchange.data.DEFAULT_FAVORITE_CURRENCIES
import com.merlinjobs.currencyexchange.data.FAVORITE_CURRENCIES
import com.merlinjobs.currencyexchange.data.repository.IPreferencesRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetFavoriteCurrenciesUseCase(mSubscrobeOnScheduler: Scheduler,
                                   mObserverOnScheduler: Scheduler) :
        SingleUseCase<List<String>, Any?>(mSubscrobeOnScheduler, mObserverOnScheduler) {

    @Inject
    lateinit var mPreferenceRepository: IPreferencesRepository

    override fun buildUseCase(params: Any?): Single<List<String>> {
        return Single.create { emitter ->
            emitter.onSuccess(Gson().fromJson(mPreferenceRepository.getData(FAVORITE_CURRENCIES)
                    ?: DEFAULT_FAVORITE_CURRENCIES, object :
                    TypeToken<List<String>>() {}.type))

        }


    }

}