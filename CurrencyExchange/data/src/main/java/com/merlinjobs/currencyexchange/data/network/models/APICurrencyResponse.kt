package com.merlinjobs.currencyexchange.data.network.models

data class APICurrencyResponse(val base: String,
                               val date: String,
                               val rates: HashMap<String, Double>)