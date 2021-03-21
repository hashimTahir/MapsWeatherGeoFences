/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network

import com.hashim.mapswithgeofencing.tokotlin.network.response.forecast.ForecastResponse
import com.hashim.mapswithgeofencing.tokotlin.network.response.nearybyplaces.PlaceResponse
import com.hashim.mapswithgeofencing.tokotlin.network.response.weather.WeatherResponse
import com.hashim.mapswithgeofencing.tokotlin.utils.Constants.Companion.H_GET_FORECAST_URL
import com.hashim.mapswithgeofencing.tokotlin.utils.Constants.Companion.H_GET_WEATHER_URL
import com.hashim.mapswithgeofencing.tokotlin.utils.Constants.Companion.H_NEARBY_PLACES_URL
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroService {
    @GET(H_GET_WEATHER_URL)
    suspend fun hGetWeather(
            @Query("lat") lat: String,
            @Query("lon") lng: String,
            @Query("APPID") key: String,
            @Query("units") unit: String
    ): WeatherResponse


    @GET(H_GET_FORECAST_URL)
    suspend fun hGetForecast(
            @Query("lat") lat: String,
            @Query("lon") lng: String,
            @Query("APPID") key: String,
            @Query("units") unit: String
    ): ForecastResponse


    @GET(H_NEARBY_PLACES_URL)
    suspend fun hFindNearByPlaces(
            @Query("location") location: String,
            @Query("radius") radius: String,
            @Query("type") type: String,
            @Query("key") key: String,
    ): PlaceResponse
}