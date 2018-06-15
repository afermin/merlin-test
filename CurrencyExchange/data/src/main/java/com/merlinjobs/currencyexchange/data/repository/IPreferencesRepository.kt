package com.merlinjobs.currencyexchange.data.repository

import android.content.Context

interface IPreferencesRepository {

    fun createLocalStorage(context: Context)

    fun storeData(key: String, param: String)

    fun getData(key: String): String?

    fun removeData(key: String)
}