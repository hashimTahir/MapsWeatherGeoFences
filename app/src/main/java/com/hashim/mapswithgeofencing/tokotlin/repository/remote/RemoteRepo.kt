package com.hashim.mapswithgeofencing.tokotlin.repository.remote

interface RemoteRepo {

    fun hGetWeather()

    fun hGetForecast()

    fun hGetDirections()

    fun hFindNearybyPlaces()

}