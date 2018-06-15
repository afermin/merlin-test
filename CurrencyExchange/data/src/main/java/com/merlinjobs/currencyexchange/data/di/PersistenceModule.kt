package com.merlinjobs.currencyexchange.data.di

import com.merlinjobs.currencyexchange.data.local.ILocalStorage
import com.merlinjobs.currencyexchange.data.local.LocalStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PersistenceModule {

    @Provides
    @Singleton
    fun providesLocalStorage(): ILocalStorage {
        return LocalStorage()
    }
}