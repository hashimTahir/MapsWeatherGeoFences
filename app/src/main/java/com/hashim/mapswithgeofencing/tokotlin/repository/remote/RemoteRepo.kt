/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.repository.remote

interface RemoteRepo {

    fun hGetWeather()

    fun hGetForecast()

    fun hGetDirections()

    fun hFindNearybyPlaces()

}