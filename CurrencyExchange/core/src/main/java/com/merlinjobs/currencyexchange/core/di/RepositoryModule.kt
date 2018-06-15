package com.merlinjobs.currencyexchange.core.di

import com.merlinjobs.currencyexchange.data.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {


    @Provides
    @Singleton
    fun providesRepositoryFactory(): IRepositoryFactory {
        return RepositoryFactory()
    }

    @Provides
    @Singleton
    fun providesExchangeRatesRepository(repositoryFactory: IRepositoryFactory): IExchangeRatesRepository {
        return repositoryFactory.getExchangeRepository()
    }

    @Provides
    @Singleton
    fun providesCurrencyRepository(repositoryFactory: IRepositoryFactory): ICurrencyRepository {
        return repositoryFactory.getCurrencyRepository()
    }

    @Provides
    @Singleton
    fun providesPreferenceRepository(repositoryFactory: IRepositoryFactory):IPreferencesRepository{
        return repositoryFactory.getPreferenceRepository()
    }

}