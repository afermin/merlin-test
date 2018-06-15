package com.merlinjobs.currencyexchange.exchange;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.merlinjobs.currencyexchange.R;
import com.merlinjobs.currencyexchange.data.local.models.Currency;
import com.merlinjobs.currencyexchange.data.local.models.ExchangeConversion;


public class ExchangeRVAdapter extends RecyclerView.Adapter<ExchangeViewHolder> {


    private Map<String, Currency> mCurrencies = new HashMap<>();

    private ArrayList<ExchangeConversion> mConversions = new ArrayList<>();


    public ExchangeRVAdapter(Map<String, Currency> mCurrencies) {
        this.mCurrencies = mCurrencies;
    }

    @Override
    public ExchangeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exchange_item, parent, false);
        return new ExchangeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ExchangeViewHolder holder, int position) {
        if (mCurrencies.containsKey(mConversions.get(position).getCurrency())) {
            holder.getMTVCurrencyAbbreviation().setText(mCurrencies
                    .get(mConversions.get(position).getCurrency())
                    .getCode());

            holder.getMTVCurrencyName().setText(mCurrencies
                    .get(mConversions.get(position).getCurrency())
                    .getName());

            holder.getMTVCurrencySymbol().setText(mCurrencies
                    .get(mConversions.get(position).getCurrency())
                    .getSymbol());


            holder.getMTVValue().setText(holder.getMTVCurrencySymbol().getText());

            holder.getMTVValue().append(String.format(Locale.getDefault(),
                    "%1$,.2f",
                    mConversions.get(position).getValue()));
        }


    }

    @Override
    public int getItemCount() {
        return mConversions.size();
    }

    public void addConversions(List<ExchangeConversion> conversions) {
        mConversions.addAll(conversions);
        notifyDataSetChanged();
        sortData();

    }

    public void clear() {
        mConversions.clear();
        notifyDataSetChanged();
        sortData();
    }

    public void sortData() {
        Collections.sort(mConversions, new Comparator<ExchangeConversion>() {
            @Override
            public int compare(ExchangeConversion o1, ExchangeConversion o2) {
                return -Double.compare(o1.getValue(), o2.getValue());
            }
        });
    }
}
