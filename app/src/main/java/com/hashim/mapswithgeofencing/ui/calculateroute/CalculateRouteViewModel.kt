/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.Domain.model.Directions
import com.hashim.mapswithgeofencing.prefrences.HlatLng
import com.hashim.mapswithgeofencing.prefrences.PrefTypes
import com.hashim.mapswithgeofencing.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.None
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.OnFindDirections
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState
import com.hashim.mapswithgeofencing.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateRouteViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        private val hSettingsPrefrences: SettingsPrefrences
) : ViewModel() {

    private val _CalculateRouteStateEvent = MutableLiveData<CalculateRouteStateEvent>()

    private val _hCalculateRouteViewState = MutableLiveData<CalculateRouteViewState>()

    val hCalculateRouteViewState: LiveData<CalculateRouteViewState>
        get() = _hCalculateRouteViewState


    val hDataState: LiveData<DataState<CalculateRouteViewState>> = Transformations
            .switchMap(_CalculateRouteStateEvent) {
                it?.let { calculateRouteViewState ->
                    hHandleStateEvent(calculateRouteViewState)
                }
            }


    private fun hHandleStateEvent(stateEvent: CalculateRouteStateEvent): LiveData<DataState<CalculateRouteViewState>>? {
        when (stateEvent) {
            is OnFindDirections -> {
                viewModelScope.launch {
                    var hCurrentLocation = Location(LocationManager.GPS_PROVIDER)
                    if (stateEvent.hStartLocation == null) {
                        val hCurrentHlatLng: HlatLng = hSettingsPrefrences.hGetSettings(PrefTypes.CURRENT_LAT_LNG_PT) as HlatLng
                        hCurrentLocation = Location(LocationManager.GPS_PROVIDER).apply {
                            latitude = hCurrentHlatLng.hLat!!
                            longitude = hCurrentHlatLng.hLng!!
                        }
                    }
                    val hDirections = hRemoteRepo.hGetDirections(
                            startLocation = hCurrentLocation,
                            endLocation = stateEvent.hDestinationLocation,
                            mode = stateEvent.hMode
                    )
                    hDrawPath(hDirections)
                }

            }
            is None -> {
            }
            else -> {
            }
        }
        return null
    }

    private fun hDrawPath(hDirections: Directions) {

        hDirections.overviewPolyline
    }


    fun hSetStateEvent(calculateRouteStateEvent: CalculateRouteStateEvent) {
        _CalculateRouteStateEvent.value = calculateRouteStateEvent
    }

    private fun hGetCurrentViewStateOrNew(): CalculateRouteViewState {
        return hCalculateRouteViewState.value ?: CalculateRouteViewState()
    }

}