package com.merlinjobs.currencyexchange.data.repository

import android.content.Context
import com.merlinjobs.currencyexchange.data.local.ILocalStorage
import javax.inject.Inject

class PreferencesRepository : IPreferencesRepository {

    @Inject
    lateinit var mLocalStorage: ILocalStorage

    override fun createLocalStorage(context: Context) {
        mLocalStorage.create(context)
    }

    override fun storeData(key: String, param: String) {
        mLocalStorage.storeData(key, param)
    }

    override fun getData(key: String): String? {
        return mLocalStorage.getData(key)
    }

    override fun removeData(key: String) {
        mLocalStorage.removeData(key)
    }


}