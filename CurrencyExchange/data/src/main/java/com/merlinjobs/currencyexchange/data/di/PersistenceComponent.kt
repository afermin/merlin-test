package com.merlinjobs.currencyexchange.data.di

import com.merlinjobs.currencyexchange.data.repository.PreferencesRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(PersistenceModule::class)])
interface PersistenceComponent {


    fun inject(preferencesRepository: PreferencesRepository)

}