/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainSharedViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo
) : ViewModel() {


    fun hHandleCategoriesCallBack() {
        Timber.d("Handle Callback ")
        val hTestLocation = Location(LocationManager.GPS_PROVIDER)
        hTestLocation.latitude = 41.43206
        hTestLocation.longitude = -81.38992


        viewModelScope.launch {

            hRemoteRepo.hReverseGeoCode(LatLng(hTestLocation.latitude, hTestLocation.longitude))
        }

    }
}