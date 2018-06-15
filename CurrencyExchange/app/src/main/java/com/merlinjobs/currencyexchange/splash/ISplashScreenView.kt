package com.merlinjobs.currencyexchange.splash

import android.content.Context
import com.merlinjobs.currencyexchange.IBaseView

interface ISplashScreenView : IBaseView {

    fun navigateToNextActivity()

    fun getContext(): Context
}