/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

class Constants {
    companion object {
        const val hTag = "hashimTimberTags %s"
        const val H_NEARBY_PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
        const val H_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json?"
        const val H_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val H_GET_WEATHER_URL = H_WEATHER_BASE_URL + "weather?"
        const val H_GET_FORECAST_URL = H_WEATHER_BASE_URL + "forecast?"
        const val H_MAPS_KEYTYPE = "hMaps"
        const val H_WEATHER_KEYTYPE = "hWeather"
        const val H_CELCIUS_UNIT = "metric"
        const val H_FARENHEIT_UNIT = "imperial"
        const val H_KELVIL_UNIT = "kelvin"

        const val H_DRIVING_MODE = "driving"
        const val H_CYCLING_MODE = "bicycling"
        const val H_WALKING_MODE = "walking"

        const val H_ENABLE_DISABLE_EMERGENCY_SETTINGS = "H_ENABLE_DISABLE_EMERGENCY_SETTINGS"
        const val H_ENABLE_DISABLE_TRACK_ME_SETTINGS = "H_ENABLE_DISABLE_TRACK_ME_SETTINGS"

    }
}