package com.merlinjobs.currencyexchange.preferences

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.merlinjobs.currencyexchange.R
import com.merlinjobs.currencyexchange.data.DEFAULT_FAVORITE_CURRENCIES
import com.merlinjobs.currencyexchange.data.local.models.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferenceAdapter(private var mCurrenciesList: ArrayList<Currency>,
                        private val mFavoritesCurrencies: ArrayList<String>) :
        RecyclerView.Adapter<PreferenceCurrencyViewHolder>() {

    private val mDefaultFavoriteCurrencyList: List<String> =
            Gson().fromJson(DEFAULT_FAVORITE_CURRENCIES, object : TypeToken<List<String>>() {}.type)

    init {
        sortCurrencies()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferenceCurrencyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.favorite_currencies_item, parent, false)
        return PreferenceCurrencyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mCurrenciesList.size
    }

/*    override fun onBindViewHolder(holder: PreferenceCurrencyViewHolder?, position: Int) {

    }*/

    override fun onBindViewHolder(holder: PreferenceCurrencyViewHolder, position: Int) {
        holder.let {
            holder.mTVCurrencyName.text = mCurrenciesList[position].name
            holder.mTVCurrencySymbol.text = mCurrenciesList[position].symbol
            holder.itemView.setBackgroundColor(if (mFavoritesCurrencies.contains(mCurrenciesList[position].code))
                holder.itemView.resources.getColor(R.color.red) else
                holder.itemView.resources.getColor(R.color.warm))

            holder.itemView.setOnClickListener {

                if (!mDefaultFavoriteCurrencyList.contains(mCurrenciesList[holder.adapterPosition].code)) {
                    if (mFavoritesCurrencies.indexOf(mCurrenciesList[holder.adapterPosition].code) >= 0) {
                        mFavoritesCurrencies.remove(mCurrenciesList[holder.adapterPosition].code)
                    } else {
                        mFavoritesCurrencies.add(mCurrenciesList[holder.adapterPosition].code)
                    }
                    sortCurrencies()
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun sortCurrencies() {
        mCurrenciesList = ArrayList(mCurrenciesList.sortedBy {
            mFavoritesCurrencies.contains(it.code)
        }.reversed())
    }

     fun getFavoritecurrencies():List<String>{
         return mFavoritesCurrencies
     }
}