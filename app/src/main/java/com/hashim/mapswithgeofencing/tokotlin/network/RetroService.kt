package com.hashim.mapswithgeofencing.tokotlin.network

import retrofit2.http.GET
import retrofit2.http.Url

interface RetroService {
    @GET
    fun hGetWeather(@Url url: String?)


}