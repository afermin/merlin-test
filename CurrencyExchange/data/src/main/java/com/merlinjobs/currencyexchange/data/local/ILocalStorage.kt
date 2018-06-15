package com.merlinjobs.currencyexchange.data.local

import android.content.Context

interface ILocalStorage {
    fun create(context: Context)
    fun storeData(key: String, param: String)
    fun getData(key: String): String?
    fun removeData(key: String)
}