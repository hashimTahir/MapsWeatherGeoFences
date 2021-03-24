/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class WeatherViewState(
        var hWeatherFields: WeatherFields = WeatherFields(),
        var hForecastFields: ForecastFields = ForecastFields(),
) : Parcelable {

    @Parcelize
    data class WeatherFields(
            val temp: String? = null
    ) : Parcelable


    @Parcelize
    data class ForecastFields(
            val temp: String? = null
    ) : Parcelable
}