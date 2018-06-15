package com.merlinjobs.currencyexchange.data.repository

import io.reactivex.Single
import android.content.Context
import com.merlinjobs.currencyexchange.data.R
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringWriter
import kotlin.collections.ArrayList


class CurrencyRepository : ICurrencyRepository {

    override var mCurrencies: List<Currency>? = null

    override fun getCurrencies(context: Context): Single<List<Currency>> {
        return Single.create { emitter ->
            if (mCurrencies === null) {
                val inputStream = context.resources.openRawResource(R.raw.currencies)
                val writer = StringWriter()
                val buffer = CharArray(1024)

                val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
                var n: Int = reader.read(buffer)
                while (n != -1) {
                    writer.write(buffer, 0, n)
                    n = reader.read(buffer)
                }

                inputStream.close()

                mCurrencies = Gson().fromJson(writer.toString(),
                        object : TypeToken<ArrayList<Currency>>() {}.type)
                if (mCurrencies !== null) {
                    emitter.onSuccess(mCurrencies!!)
                } else {
                    emitter.onError(NullPointerException())
                }
            } else {
                emitter.onSuccess(mCurrencies!!)
            }


        }
    }


}