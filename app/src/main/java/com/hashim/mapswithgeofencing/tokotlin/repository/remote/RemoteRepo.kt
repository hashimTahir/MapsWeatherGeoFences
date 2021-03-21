/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category

interface RemoteRepo {

    fun hGetWeather()

    fun hGetForecast()

    fun hGetDirections()


    suspend fun hFindNearybyPlaces(category: Category, location: Location)
}