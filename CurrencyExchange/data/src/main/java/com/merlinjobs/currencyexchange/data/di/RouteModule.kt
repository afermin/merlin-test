package com.merlinjobs.currencyexchange.data.di

import com.merlinjobs.currencyexchange.data.network.routes.IConvertRoute
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Singleton
@Module(includes = [(NetworkModule::class)])
class RouteModule {

    @Provides
    fun provideConvertRoute(retrofit: Retrofit): IConvertRoute = retrofit.create(IConvertRoute::class.java)

}