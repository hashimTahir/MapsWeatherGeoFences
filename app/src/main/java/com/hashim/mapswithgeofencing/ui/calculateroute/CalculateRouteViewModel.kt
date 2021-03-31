/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.calculateroute

import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.hashim.mapswithgeofencing.Domain.model.Directions
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.prefrences.HlatLng
import com.hashim.mapswithgeofencing.prefrences.PrefTypes.*
import com.hashim.mapswithgeofencing.prefrences.SettingsPrefrences
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState
import com.hashim.mapswithgeofencing.ui.events.CalculateRouteViewState.*
import com.hashim.mapswithgeofencing.utils.DataState
import com.hashim.mapswithgeofencing.utils.MarkerUtils.MarkerType.CURRENT
import com.hashim.mapswithgeofencing.utils.MarkerUtils.MarkerType.DESTINATION
import com.hashim.mapswithgeofencing.utils.hCreateMarkerOptions
import com.hashim.mapswithgeofencing.utils.hLatLngToLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CalculateRouteViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        private val hSettingsPrefrences: SettingsPrefrences,
        @ApplicationContext private val hContext: Context,
) : ViewModel() {

    private val _CalculateRouteStateEvent = MutableLiveData<CalculateRouteStateEvent>()

    private val _hCalculateRouteViewState = MutableLiveData<CalculateRouteViewState>()
    var hLastOnFindDirections: OnFindDirections? = null

    val hCalculateRouteViewState: LiveData<CalculateRouteViewState>
        get() = _hCalculateRouteViewState


    val hDataState: LiveData<DataState<CalculateRouteViewState>> = Transformations
            .switchMap(_CalculateRouteStateEvent) {
                it?.let { calculateRouteViewState ->
                    hHandleStateEvent(calculateRouteViewState)
                }
            }


    private fun hHandleStateEvent(stateEvent: CalculateRouteStateEvent):
            LiveData<DataState<CalculateRouteViewState>>? {
        when (stateEvent) {
            is OnFindDirections -> {
                hLastOnFindDirections = stateEvent
                hFindDirections(hLastOnFindDirections, DirectionsMode.DRIVING)

            }
            is OnMapReady -> {
                hSetCurrentLocation()

            }
            is OnModeChanged -> {
                hFindDirections(hLastOnFindDirections, stateEvent.hMode)
            }
            is None -> {
            }
            else -> {
            }
        }
        return null
    }

    private fun hFindDirections(stateEvent: OnFindDirections?, hMode: DirectionsMode) {
        viewModelScope.launch {
            var hCurrentLocation: Location
            if (stateEvent?.hStartLocation == null) {
                val hCurrentHlatLng: HlatLng = hSettingsPrefrences
                        .hGetSettings(CURRENT_LAT_LNG_PT) as HlatLng
                hCurrentLocation = hLatLngToLocation(
                        hLat = hCurrentHlatLng.hLat!!,
                        hLng = hCurrentHlatLng.hLng!!,
                )

            } else {
                hCurrentLocation = stateEvent.hStartLocation
            }
            val hDirections = hRemoteRepo.hGetDirections(
                    startLocation = hCurrentLocation,
                    endLocation = stateEvent?.hDestinationLocation!!,
                    mode = hMode
            )
            hDrawPath(hDirections)
        }
    }

    private fun hSetCurrentLocation() {
        val hCurrentHlatLng: HlatLng = hSettingsPrefrences.hGetSettings(CURRENT_LAT_LNG_PT) as HlatLng

        val hCurrentLocation = hLatLngToLocation(
                hLat = hCurrentHlatLng.hLat!!,
                hLng = hCurrentHlatLng.hLng!!,
        )
        val hMapType: Int? = hSettingsPrefrences.hGetSettings(MAPS_TYPE_PT) as Int?

        _hCalculateRouteViewState.value = CalculateRouteViewState(
                hCalculateRouteFields = CalculateRouteFields(
                        hSetMapVS = SetMapVS(
                                currentLocation = hCurrentLocation,
                                cameraZoom = 12.0F,
                                hMapType = hMapType
                        )
                )
        )
    }


    private fun hDrawPath(hDirections: Directions) {

        val hDistance: String

        val hUnit = hSettingsPrefrences.hGetSettings(DISTANCE_UNIT_PT) as Int?
        hDistance = when (hUnit) {
            1 -> {
                hDirections.distance?.value?.div(1600).toString()
            }
            else -> {
                hDirections.distance?.text.toString()
            }
        }
        _hCalculateRouteViewState.value = CalculateRouteViewState(
                hCalculateRouteFields = CalculateRouteFields(
                        hDrawPathVS = DrawPathVS(
                                hDistance = hDirections.distance,
                                hOverviewPolyline = hDirections.overviewPolyline,
                                hSteps = hDirections.steps,
                                hDistanceUnit = hDistance,
                                hEta = String.format(hContext.getString(R.string.time), " ${hDirections.duration?.text}"),
                                hStartMarker = hCreateMarkerOptions(
                                        hContext = hContext,
                                        hLat = hDirections.startLocation?.lat!!,
                                        hLng = hDirections.startLocation.lng,
                                        hType = CURRENT,
                                ),
                                hEndMarker = hCreateMarkerOptions(
                                        hContext = hContext,
                                        hLat = hDirections.endLocation?.lat!!,
                                        hLng = hDirections.endLocation.lng,
                                        hType = DESTINATION,
                                )
                        )
                )
        )

    }


    fun hSetStateEvent(calculateRouteStateEvent: CalculateRouteStateEvent) {
        _CalculateRouteStateEvent.value = calculateRouteStateEvent
    }


    fun hSetDrawPathVs(drawPathVS: DrawPathVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hCalculateRouteFields.hDrawPathVS = drawPathVS
        _hCalculateRouteViewState.value = hUpdate
    }

    private fun hGetCurrentViewStateOrNew(): CalculateRouteViewState {
        return hCalculateRouteViewState.value ?: CalculateRouteViewState()
    }

    fun hSetCurrentLocationVs(setMapVS: SetMapVS) {
        val hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hCalculateRouteFields.hSetMapVS = setMapVS
        _hCalculateRouteViewState.value = hUpdate
    }

}