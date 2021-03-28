/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.weather

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.Domain.model.Forecast
import com.hashim.mapswithgeofencing.Domain.model.Weather
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.WeatherStateEvent
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState
import com.hashim.mapswithgeofencing.ui.events.WeatherViewState.*
import com.hashim.mapswithgeofencing.utils.Constants
import com.hashim.mapswithgeofencing.utils.DataState
import com.hashim.mapswithgeofencing.utils.DateFormatter
import com.hashim.mapswithgeofencing.utils.DateFormatter.FormatterType.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo
) : ViewModel() {


    private val _hWeatherStateEvent = MutableLiveData<WeatherStateEvent>()
    private var hCurrentLocation: Location? = null
    private var hLastDayName: String? = null
    private val _hWeatherViewState = MutableLiveData<WeatherViewState>()
    val hWeatherViewState: LiveData<WeatherViewState>
        get() = _hWeatherViewState


    val hDataState: LiveData<DataState<WeatherViewState>> = Transformations
            .switchMap(_hWeatherStateEvent) {
                it?.let { weatherStateEvent ->
                    hHandleStateEvent(weatherStateEvent)
                }
            }

    private fun hHandleStateEvent(weatherStateEvent: WeatherStateEvent): LiveData<DataState<WeatherViewState>>? {
        Timber.d("hHandleStateEvent ${weatherStateEvent.javaClass}")
        // TODO: 25-Mar-21  Get temprature unit from the settings
        when (weatherStateEvent) {
            is WeatherStateEvent.OnFetchForecast -> {
                val hLocation = hCreateLocationObject(weatherStateEvent.hLat, weatherStateEvent.hLng)
                viewModelScope.launch {
                    val hForecast = hRemoteRepo.hGetForecast(
                            location = hLocation,
                            Constants.H_CELCIUS_UNIT,
                    )
                    hFormatForecastData(hForecast)

                }
            }
            is WeatherStateEvent.OnFetchWeather -> {
                val hLocation = hCreateLocationObject(weatherStateEvent.hLat, weatherStateEvent.hLng)
                viewModelScope.launch {
                    val hWeather = hRemoteRepo.hGetWeather(
                            location = hLocation,
                            Constants.H_CELCIUS_UNIT,
                    )
                    hFormatWeatherData(hWeather)
                }

            }
            is WeatherStateEvent.None -> {
            }
        }
        return null

    }

    private fun hFormatWeatherData(weather: Weather) {
        val hCalendar = Calendar.getInstance()
        val hIcon: String = weather.icon!!

        _hWeatherViewState.value = WeatherViewState(
                hWeatherFields = WeatherFields(
                        hWeatherVS = WeatherVS(
                                hDay = DateFormatter.hGetSimpleFormatter(DAYNAME_MONTH_DATE).format(hCalendar.time),
                                hTime = DateFormatter.hGetSimpleFormatter(HRS_MINS).format(hCalendar.time),
                                hPressure = weather.pressure.toString(),
                                hHumidity = weather.humidity.toString(),
                                hDescription = weather.description,
                                hCountry = weather.country,
                                hIconUrl = String.format(Constants.H_ICON_URL, hIcon),
                                hTemperature = weather.tempMax.toString()
                        ),
                        hForecastVS = null

                )
        )
    }

    private fun hFormatForecastData(hForecast: Forecast) {
        val hTodaysList = mutableListOf<TodaysForeCast>()
        val hWeeklyList = mutableListOf<WeekForecast>()
        val hCalendar = Calendar.getInstance()

        val hTodaysDate = DateFormatter.hGetSimpleFormatter(JUST_DATE).format(hCalendar.time).toInt()

        for (x in hForecast.list) {

            hCalendar.time = DateFormatter.hGetSimpleFormatter(YEAR_MONTH_DAY_HRS_MINS_SECS).parse(x.dtTxt)

            val hLiveDate: Int = DateFormatter.hGetSimpleFormatter(JUST_DATE).format(hCalendar.time).toInt()


            if (hLiveDate <= hTodaysDate) {
                hTodaysList.add(
                        TodaysForeCast(
                                description = x.weather.get(0).description,
                                icon = String.format(Constants.H_ICON_URL, x.weather.get(0).icon),
                                tempMax = x.main.tempMax,
                                date = DateFormatter.hGetSimpleFormatter(YEAR_MONTH_DAY).format(hCalendar.time),
                                time = DateFormatter.hGetSimpleFormatter(HRS_MINS).format(hCalendar.time),
                        )
                )
            } else {
                val hNameOfDay = DateFormatter.hGetSimpleFormatter(JUST_DAY_NAME).format(hCalendar.time)
                if (hNameOfDay.equals(hLastDayName)) {
                    continue
                } else {
                    hWeeklyList.add(
                            WeekForecast(
                                    description = x.weather.get(0).description,
                                    icon = String.format(Constants.H_ICON_URL, x.weather.get(0).icon),
                                    tempMax = x.main.tempMax,
                                    tempMin = x.main.tempMin,
                                    time = DateFormatter.hGetSimpleFormatter(HRS_MINS).format(hCalendar.time),
                            )
                    )
                }
                hLastDayName = hNameOfDay
            }
        }
        _hWeatherViewState.value = WeatherViewState(
                hWeatherFields = WeatherFields(
                        hWeatherVS = null,
                        hForecastVS = ForecastVS(
                                hWeeksList = hWeeklyList,
                                hTodaysList = hTodaysList
                        )
                )
        )


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

    fun hSetForecastData(forecastVS: ForecastVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hWeatherFields.hForecastVS = forecastVS
        _hWeatherViewState.value = hUpdate
    }

    fun hSetWeatherData(weatherVSFields: WeatherViewState.WeatherVS?) {
        var hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hWeatherFields.hWeatherVS = weatherVSFields
        _hWeatherViewState.value = hUpdate
    }

    private fun hGetCurrentViewStateOrNew(): WeatherViewState {
        val hValue = hWeatherViewState.value?.let {
            it
        } ?: WeatherViewState()
        return hValue
    }


}