package com.merlinjobs.currencyexchange.data.repository

import com.merlinjobs.currencyexchange.data.di.DaggerPersistenceComponent
import com.merlinjobs.currencyexchange.data.di.DaggerRoutesComponent
import com.merlinjobs.currencyexchange.data.di.NetworkModule
import com.merlinjobs.currencyexchange.data.di.PersistenceModule

class RepositoryFactory : IRepositoryFactory {


    private val mComponent by lazy {
        DaggerRoutesComponent.builder()
                .networkModule(NetworkModule())
                .build()

    }

    private val mPreferenceComponent =
            DaggerPersistenceComponent.builder()
                    .persistenceModule(PersistenceModule())
                    .build()


    private var mExchangeRatesRepository: ExchangeRatesRepository? = null


    private var mCurrencyRepository: CurrencyRepository? = null

    private var mPreferencesRepository: PreferencesRepository? = null


    override fun getCurrencyRepository(): ICurrencyRepository {
        if (mCurrencyRepository === null) {
            mCurrencyRepository = CurrencyRepository()
        }

        return mCurrencyRepository!!
    }

    override fun getExchangeRepository(): IExchangeRatesRepository {
        if (mExchangeRatesRepository === null) {
            mExchangeRatesRepository = ExchangeRatesRepository()
            mComponent.inject(mExchangeRatesRepository!!)
        }
        return mExchangeRatesRepository!!
    }

    override fun getPreferenceRepository(): IPreferencesRepository {
        if (mPreferencesRepository === null) {
            mPreferencesRepository = PreferencesRepository()
            mPreferenceComponent.inject(mPreferencesRepository!!)
        }

        return mPreferencesRepository!!
    }


}