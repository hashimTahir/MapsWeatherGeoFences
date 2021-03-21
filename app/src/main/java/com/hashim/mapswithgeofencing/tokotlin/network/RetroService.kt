/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network

import com.hashim.mapswithgeofencing.tokotlin.network.response.nearybyplaces.PlaceResponse
import com.hashim.mapswithgeofencing.tokotlin.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface RetroService {
    @GET
    fun hGetWeather(@Url url: String?)


    @GET(Constants.H_NEARBY_PLACES_URL)
    suspend fun hFindNearByPlaces(
            @Query("location") location: String,
            @Query("radius") radius: String,
            @Query("type") type: String,
            @Query("key") key: String,
    ): PlaceResponse
}