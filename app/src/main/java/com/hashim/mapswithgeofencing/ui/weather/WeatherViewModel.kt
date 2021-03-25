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
import com.hashim.mapswithgeofencing.utils.Constants
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

        when (weatherStateEvent) {
            is WeatherStateEvent.OnFetchForecast -> {
            }
            is WeatherStateEvent.OnFetchWeather -> {
                val hLocation = Location(LocationManager.GPS_PROVIDER)
                hLocation.latitude = weatherStateEvent.hLat!!
                hLocation.longitude = weatherStateEvent.hLng!!
                viewModelScope.launch {
                    val hWeather = hRemoteRepo.hGetWeather(
                            location = hLocation,
                            Constants.H_CELCIUS_UNIT,
                    )
                    hWeather
                }

            }
            is WeatherStateEvent.None -> {
            }
        }
        return null

    }

    fun hSetStateEvent(weatherStateEvent: WeatherStateEvent) {
        _hWeatherStateEvent.value = weatherStateEvent
    }

}