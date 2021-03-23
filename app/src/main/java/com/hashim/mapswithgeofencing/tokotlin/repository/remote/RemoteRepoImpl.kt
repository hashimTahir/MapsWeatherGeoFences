/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.tokotlin.network.RetroService
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category
import timber.log.Timber

class RemoteRepoImpl(
        private val hRetroService: RetroService,
        private val hMapsKey: String,
        private val hWeatherKey: String
) : RemoteRepo {

    override suspend fun hGetWeather(location: Location, unitType: String) {
        Timber.d("Maps key $hMapsKey")
        Timber.d("Weather key $hWeatherKey")
        hRetroService.hGetWeather(
                lat = location.latitude.toString(),
                lng = location.longitude.toString(),
                key = hWeatherKey,
                unit = unitType,
        )
    }

    override suspend fun hGetForecast(location: Location, unitType: String) {
        hRetroService.hGetForecast(
                lat = location.latitude.toString(),
                lng = location.longitude.toString(),
                key = hWeatherKey,
                unit = unitType,
        )
    }


    override suspend fun hGetDirections(startLocation: Location, endLocation: Location, mode: String) {
        hRetroService.hFindDirections(
                startLocation = "${startLocation.latitude},${startLocation.longitude}",
                endLocation = "${endLocation.latitude},${endLocation.longitude}",
                key = hMapsKey,
                mode = mode,
        )
    }

    override suspend fun hFindNearybyPlaces(category: Category, location: Location) {

        var hFindNearByPlaces = hRetroService.hFindNearByPlaces(
                location = "${location.latitude},${location.longitude}",
                radius = "1000",
                type = category.name,
                key = hMapsKey
        )
    }

}