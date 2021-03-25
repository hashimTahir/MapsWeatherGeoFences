/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.remote

import android.location.Location
import com.hashim.mapswithgeofencing.Domain.model.Forecast
import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.Domain.model.Weather
import com.hashim.mapswithgeofencing.network.RetroService
import com.hashim.mapswithgeofencing.repository.mappers.ForecastDtoMapper
import com.hashim.mapswithgeofencing.repository.mappers.NearByPlacesDtoMapper
import com.hashim.mapswithgeofencing.repository.mappers.WeatherDtoMapper
import com.hashim.mapswithgeofencing.ui.main.Category

class RemoteRepoImpl(
        private val hRetroService: RetroService,
        private val hNearByPlacesDtoMapper: NearByPlacesDtoMapper,
        private val hWeatherDtoMapper: WeatherDtoMapper,
        private val hForecastDtoMapper: ForecastDtoMapper,
        private val hMapsKey: String,
        private val hWeatherKey: String
) : RemoteRepo {

    override suspend fun hGetWeather(location: Location, unitType: String): Weather {
        val hGetWeather = hRetroService.hGetWeather(
                lat = location.latitude.toString(),
                lng = location.longitude.toString(),
                key = hWeatherKey,
                unit = unitType,
        )
        return hWeatherDtoMapper.hMapToDomainModel(hGetWeather)
    }

    override suspend fun hGetForecast(location: Location, unitType: String): Forecast {
        val hGetForecast = hRetroService.hGetForecast(
                lat = location.latitude.toString(),
                lng = location.longitude.toString(),
                key = hWeatherKey,
                unit = unitType,
        )
        return hForecastDtoMapper.hMapToDomainModel(hGetForecast)
    }


    override suspend fun hGetDirections(startLocation: Location, endLocation: Location, mode: String) {
        hRetroService.hFindDirections(
                startLocation = "${startLocation.latitude},${startLocation.longitude}",
                endLocation = "${endLocation.latitude},${endLocation.longitude}",
                key = hMapsKey,
                mode = mode,
        )
    }

    override suspend fun hFindNearybyPlaces(category: Category, location: Location): List<NearByPlaces> {

        val hFindNearByPlaces = hRetroService.hFindNearByPlaces(
                location = "${location.latitude},${location.longitude}",
                radius = "1000",
                type = category.name,
                key = hMapsKey
        )

        return hNearByPlacesDtoMapper.hToDomainList(hFindNearByPlaces.nearyByPlacesResultDtos)
    }
}