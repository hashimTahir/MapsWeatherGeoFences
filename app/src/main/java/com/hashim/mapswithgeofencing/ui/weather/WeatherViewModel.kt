/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.weather

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState.ForecastFields
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState.WeatherFields
import com.hashim.mapswithgeofencing.utils.Constants
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo
) : ViewModel() {


    private val _hWeatherStateEvent = MutableLiveData<WeatherStateEvent>()
    private var hCurrentLocation: Location? = null

    private val _hWeatherViewState = MutableLiveData<WeatherViewState>()
    public val hWeatherViewState: LiveData<WeatherViewState>
        get() = _hWeatherViewState


    val hDataState: LiveData<DataState<WeatherViewState>> = Transformations
            .switchMap(_hWeatherStateEvent) {
                it?.let { weatherStateEvent ->
                    hHandleStateEvent(weatherStateEvent)
                }
            }

    private fun hHandleStateEvent(weatherStateEvent: WeatherStateEvent): LiveData<DataState<WeatherViewState>>? {
        Timber.d("hHandleStateEvent ${weatherStateEvent.javaClass}")
        when (weatherStateEvent) {
            is WeatherStateEvent.OnFetchForecast -> {
                val hLocation = hCreateLocationObject(weatherStateEvent.hLat, weatherStateEvent.hLng)
                viewModelScope.launch {
                    val hForecast = hRemoteRepo.hGetForecast(
                            location = hLocation,
                            Constants.H_CELCIUS_UNIT,
                    )
                    _hWeatherViewState.value = WeatherViewState(
                            hWeatherFields = WeatherFields(),
                            hForecastFields = ForecastFields(
                                    hForecast
                            )
                    )
                }
            }
            is WeatherStateEvent.OnFetchWeather -> {
                val hLocation = hCreateLocationObject(weatherStateEvent.hLat, weatherStateEvent.hLng)
                viewModelScope.launch {
                    val hWeather = hRemoteRepo.hGetWeather(
                            location = hLocation,
                            Constants.H_CELCIUS_UNIT,
                    )
                    _hWeatherViewState.value = WeatherViewState(
                            hWeatherFields = WeatherFields(
                                    hWeather
                            ),
                            hForecastFields = ForecastFields()
                    )
                }

            }
            is WeatherStateEvent.None -> {
            }
        }
        return null

    }

    private fun hCreateLocationObject(lat: Double?, hLng: Double?): Location {
        val hLocation = Location(LocationManager.GPS_PROVIDER)
        if (lat != null) {
            hLocation.latitude = lat
        }
        if (hLng != null) {
            hLocation.longitude = hLng
        }
        return hLocation
    }

    fun hSetStateEvent(weatherStateEvent: WeatherStateEvent) {
        _hWeatherStateEvent.value = weatherStateEvent
    }

    fun hSetForecastData(forecastFields: ForecastFields) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hForecastFields = forecastFields
        _hWeatherViewState.value = hUpdate
    }

    fun hSetWeatherData(weatherFields: WeatherFields) {
        var hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hWeatherFields = weatherFields
        _hWeatherViewState.value = hUpdate
    }

    fun hGetCurrentViewStateOrNew(): WeatherViewState {
        val hValue = hWeatherViewState.value?.let {
            it
        } ?: WeatherViewState()
        return hValue
    }

}