/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.Domain.model.Weather
import com.hashim.mapswithgeofencing.ui.main.Category

interface RemoteRepo {

    suspend fun hGetWeather(location: Location, unitType: String): Weather

    suspend fun hGetForecast(location: Location, unitType: String)

    suspend fun hGetDirections(startLocation: Location, endLocation: Location, mode: String)

    suspend fun hFindNearybyPlaces(category: Category, location: Location): List<NearByPlaces>

}