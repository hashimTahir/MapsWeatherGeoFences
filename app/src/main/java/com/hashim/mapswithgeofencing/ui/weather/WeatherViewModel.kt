/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.weather

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo
) : ViewModel() {


    private fun hRequestWeather(location: Location?) {
        viewModelScope.launch {
            location?.let {
                hRemoteRepo.hGetWeather(
                        location = location,
                        unitType = Constants.H_CELCIUS_UNIT
                )
            }
        }
    }

    private fun hRequestForecast(location: Location?) {
        viewModelScope.launch {
            location?.let {
                hRemoteRepo.hGetForecast(
                        location = location,
                        unitType = Constants.H_CELCIUS_UNIT
                )
            }
        }
    }


}