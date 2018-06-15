package com.merlinjobs.currencyexchange;

import android.app.Application;

import java.util.List;

import com.merlinjobs.currencyexchange.data.local.models.Currency;
import com.merlinjobs.currencyexchange.di.DaggerUseCaseComponent;
import com.merlinjobs.currencyexchange.di.UseCaseComponent;
import com.merlinjobs.currencyexchange.di.UseCaseModule;

public class BaseApplication extends Application {


    private static BaseApplication instance;

    private UseCaseComponent mUseCaseComponent;

    private List<Currency> mCurrencies;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }


    public UseCaseComponent getUseCaseComponent() {
        if (mUseCaseComponent == null) {
            mUseCaseComponent = DaggerUseCaseComponent.builder()
                    .useCaseModule(new UseCaseModule())
                    .build();
        }
        return mUseCaseComponent;
    }


    public static BaseApplication getInstance() {
        return instance;
    }

    public List<Currency> getmCurrencies() {
        return mCurrencies;
    }

    public void setmCurrencies(List<Currency> mCurrencies) {
        this.mCurrencies = mCurrencies;
    }
}
