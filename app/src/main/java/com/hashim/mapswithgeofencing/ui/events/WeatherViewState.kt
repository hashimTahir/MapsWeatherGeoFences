/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.os.Parcelable
import com.hashim.mapswithgeofencing.Domain.model.Forecast
import com.hashim.mapswithgeofencing.Domain.model.Weather
import kotlinx.android.parcel.Parcelize


data class WeatherViewState(
        var hWeatherFields: WeatherFields = WeatherFields(),
        var hForecastFields: ForecastFields = ForecastFields(),
)  {

    data class WeatherFields(
            val hWeather: Weather? = null
    )


    data class ForecastFields(
            val hForecast: Forecast? = null
    )
}