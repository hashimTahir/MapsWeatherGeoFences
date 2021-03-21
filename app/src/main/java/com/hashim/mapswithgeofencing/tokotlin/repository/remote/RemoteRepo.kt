/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category

interface RemoteRepo {

    suspend fun hGetWeather(location: Location, unitType: String)

    suspend fun hGetForecast(location: Location, unitType: String)

    suspend fun hGetDirections(startLocation: Location, endLocation: Location, mode: String)

    suspend fun hFindNearybyPlaces(category: Category, location: Location)

}