/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import com.hashim.mapswithgeofencing.ui.main.Category

sealed class WeatherStateEvent {
    class OnFetchWeather(val hLat: Double?, hLng: Double?) : WeatherStateEvent()

    class OnFetchForecast(val category: Category) : WeatherStateEvent()

    class None : WeatherStateEvent()
}