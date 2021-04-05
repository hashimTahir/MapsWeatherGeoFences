/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network

class RetroConstants {
    companion object {
        const val H_NEARBY_PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
        const val H_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json?"
        const val H_GEO_CODE_URL = "https://maps.googleapis.com/maps/api/geocode/json?"
        const val H_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val H_GET_WEATHER_URL = H_WEATHER_BASE_URL + "weather?"
        const val H_GET_FORECAST_URL = H_WEATHER_BASE_URL + "forecast?"
    }
}