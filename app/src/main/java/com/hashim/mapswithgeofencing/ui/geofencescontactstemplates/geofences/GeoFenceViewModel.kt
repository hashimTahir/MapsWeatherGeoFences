/*
 * Copyright (c) 2021/  4/ 7.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.hashim.mapswithgeofencing.ui.geofencescontactstemplates.geofences.GeoFenceStateEvent.*
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GeoFenceViewModel @Inject constructor(
        @ApplicationContext val hContext: Context
) : ViewModel() {
    private val _hGeoFenceViewState = MutableLiveData<GeoFenceViewState>()
    private val _hGeoFenceStatEvent = MutableLiveData<GeoFenceStateEvent>()

    val hGeoFenceViewState: LiveData<GeoFenceViewState>
        get() = _hGeoFenceViewState

    val hDataState: LiveData<DataState<GeoFenceViewState>> =
            Transformations.switchMap(_hGeoFenceStatEvent) {
                hSetStateEvent(it)
            }


    fun hSetStateEvent(geoFenceStateEvent: GeoFenceStateEvent):
            LiveData<DataState<GeoFenceViewState>>? {
        when (geoFenceStateEvent) {
            is OnRadiusChanged -> {
                Timber.d("OnRadius Changed ${geoFenceStateEvent.hProgress}  float ${geoFenceStateEvent.hProgressFloat}.")
            }
            is OnMapClicked -> {
            }
            is OnGeoFenceSaved -> {
            }
            is OnDisplaySavedFences -> {
            }
            is None -> {
            }
        }
        return null
    }

}