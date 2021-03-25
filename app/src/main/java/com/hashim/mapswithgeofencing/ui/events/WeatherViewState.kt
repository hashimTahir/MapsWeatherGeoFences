/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events


data class WeatherViewState(
        var hWeatherFields: WeatherFields = WeatherFields(),
        var hForecastFields: ForecastFields = ForecastFields()
) {

    data class WeatherFields(
            val hDay: String? = null,
            val hTime: String? = null,
            val hIconUrl: String? = null,
            val hPressure: String? = null,
            val hHumidity: String? = null,
            val hCountry: String? = null,
            val hDescription: String? = null,
            val hTemperature: String? = null,
    )

    data class ForecastFields(
            val hTodaysList: List<TodaysForeCast>? = null,
            val hWeeksList: List<WeekForecast>? = null,
    )

    data class TodaysForeCast(
            val description: String? = null,
            val icon: String? = null,
            val tempMax: Double? = null,
            val date: String? = null,
            val time: String? = null
    )

    data class WeekForecast(
            val description: String? = null,
            val icon: String? = null,
            val tempMax: Double? = null,
            val tempMin: Double? = null,
            val time: String? = null
    )

}