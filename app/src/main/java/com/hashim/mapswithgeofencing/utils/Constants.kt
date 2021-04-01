/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.utils

class Constants {
    companion object {
        val H_REQUEST_CODE = 38
        val H_BOTTOM_DIALOG = "hBottomDialog"
        val CHANNEL_ID: String = "channelIdHere"
        val H_NOTIFICATION_ID: Int = 1
        const val hTag = "hashimTimberTags %s"
        const val H_NEARBY_PLACES_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
        const val H_DIRECTIONS_URL = "https://maps.googleapis.com/maps/api/directions/json?"
        const val H_WEATHER_BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val H_ICON_URL = "https://openweathermap.org/img/wn/%s@2x.png"
        const val H_GET_WEATHER_URL = H_WEATHER_BASE_URL + "weather?"
        const val H_GET_FORECAST_URL = H_WEATHER_BASE_URL + "forecast?"
        const val H_MAPS_KEYTYPE = "hMaps"
        const val H_WEATHER_KEYTYPE = "hWeather"
        const val H_CELCIUS_UNIT = "metric"
        const val H_FARENHEIT_UNIT = "imperial"
        const val H_KELVIL_UNIT = "kelvin"
        const val H_DATABASE = "contacts_db"




        const val H_SAVED_LIST = 678
        const val H_ALL_LIST = 910
        const val H_T_SAVED_LIST = 673
        const val MENU_ITEM_MESSAGE = 34


    }
}