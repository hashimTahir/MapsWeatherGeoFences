/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashim.mapswithgeofencing.tokotlin.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.tokotlin.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainSharedViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo
) : ViewModel() {


    fun hHandleCategoriesCallBack(category: Category, location: Location?) {
        Timber.d("Handle Callback $category")


//        hTestNearByPlaces(location, category)
//        hTestWeather(location)
        hTestForecast(location)
    }

    private fun hTestWeather(location: Location?) {
        viewModelScope.launch {
            location?.let {
                hRemoteRepo.hGetWeather(
                        location = location,
                        unitType = Constants.H_CELCIUS_UNIT
                )
            }
        }
    }

    private fun hTestForecast(location: Location?) {
        viewModelScope.launch {
            location?.let {
                hRemoteRepo.hGetForecast(
                        location = location,
                        unitType = Constants.H_CELCIUS_UNIT
                )
            }
        }
    }


    private fun hTestNearByPlaces(location: Location?, category: Category) {
        viewModelScope.launch {
            location?.let {
                hRemoteRepo.hFindNearybyPlaces(
                        category = category,
                        location = it
                )
            }

        }
    }
}