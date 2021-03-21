/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.tokotlin.network.RetroService
import com.hashim.mapswithgeofencing.tokotlin.ui.main.Category

class RemoteRepoImpl(
        private val hRetroService: RetroService,
        private val hKey: String
) : RemoteRepo {
    override fun hGetWeather() {

        TODO("Not yet implemented")
    }

    override fun hGetForecast() {
        TODO("Not yet implemented")
    }


    override fun hGetDirections() {
        TODO("Not yet implemented")
    }

    override suspend fun hFindNearybyPlaces(category: Category, location: Location) {

        hRetroService.hFindNearByPlaces(
                location = "${location.latitude},${location.longitude}",
                radius = "1000",
                type = category.name,
                key = hKey
        )
    }

}