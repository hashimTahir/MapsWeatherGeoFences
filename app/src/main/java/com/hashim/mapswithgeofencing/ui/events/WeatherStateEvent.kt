/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

sealed class WeatherStateEvent {
    class OnFetchWeather(
            val hLat: Double?,
            val hLng: Double?
    ) : WeatherStateEvent()

    class OnFetchForecast(
            val hLat: Double?,
            val hLng: Double?
    ) : WeatherStateEvent()

    class None : WeatherStateEvent()
}