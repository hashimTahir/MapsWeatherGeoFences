/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.remote

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.hashim.mapswithgeofencing.Domain.model.*
import com.hashim.mapswithgeofencing.network.RetroService
import com.hashim.mapswithgeofencing.repository.mappers.*
import com.hashim.mapswithgeofencing.ui.calculateroute.DirectionsMode
import com.hashim.mapswithgeofencing.ui.main.fragments.adapter.Category
import java.util.*

class RemoteRepoImpl(
        private val hRetroService: RetroService,
        private val hNearByPlacesDtoMapper: NearByPlacesDtoMapper,
        private val hWeatherDtoMapper: WeatherDtoMapper,
        private val hForecastDtoMapper: ForecastDtoMapper,
        private val hDirectionDtoMapper: DirectionDtoMapper,
        private val hGeoCodeDtoMapper: GeoCodeDtoMapper,
        private val hPlaceSuggestionDtoMapper: PlaceSuggestionDtoMapper,
        private val hMapsKey: String,
        private val hWeatherKey: String
) : RemoteRepo {

    override suspend fun hGetWeather(
            location: Location,
            unitType: String
    ): Weather {
        val hGetWeather = hRetroService.hGetWeather(
                lat = location.latitude.toString(),
                lng = location.longitude.toString(),
                key = hWeatherKey,
                unit = unitType,
        )
        return hWeatherDtoMapper.hMapToDomainModel(hGetWeather)
    }

    override suspend fun hGetForecast(
            location: Location,
            unitType: String
    ): Forecast {
        val hGetForecast = hRetroService.hGetForecast(
                lat = location.latitude.toString(),
                lng = location.longitude.toString(),
                key = hWeatherKey,
                unit = unitType,
        )
        return hForecastDtoMapper.hMapToDomainModel(hGetForecast)
    }


    override suspend fun hGetDirections(
            startLocation: Location,
            endLocation: Location,
            mode: DirectionsMode): Directions {
        val hGetDirections = hRetroService.hFindDirections(
                startLocation = "${startLocation.latitude},${startLocation.longitude}",
                endLocation = "${endLocation.latitude},${endLocation.longitude}",
                key = hMapsKey,
                mode = mode.name.toLowerCase(Locale.getDefault()),
        )

        return hDirectionDtoMapper.hMapToDomainModel(hGetDirections)
    }

    override suspend fun hFindNearybyPlaces(
            category: Category,
            location: Location,
            hRadius: Int
    ): List<NearByPlaces> {

        val hFindNearByPlaces = hRetroService.hFindNearByPlaces(
                location = "${location.latitude},${location.longitude}",
                radius = hRadius.toString(),
                type = category.name,
                key = hMapsKey
        )

        return hNearByPlacesDtoMapper.hToDomainList(hFindNearByPlaces.nearyByPlacesResultDtos)
    }

    override suspend fun hReverseGeoCode(latLng: LatLng): List<GeoCode> {
        val hReverseGeoCode = hRetroService.hGeoCode(
                key = hMapsKey,
                latLng = "${latLng.latitude},${latLng.longitude}"
        )

        return hGeoCodeDtoMapper.hToDomainList(hReverseGeoCode.results)
    }

    override suspend fun hGetPlacesAutoComplete(
            query: String,
            radius: Int?
    ): List<PlaceSuggestions> {
        val hPlaceSuggestions = hRetroService.hGetPlacesAutoComplete(
                inputText = query,
                key = hMapsKey,
                radius = radius.toString()
        )

        return hPlaceSuggestionDtoMapper.hToDomainList(hPlaceSuggestions.predictions)
    }
}