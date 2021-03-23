/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.Helper.Constants
import com.hashim.mapswithgeofencing.Helper.MarkerUtils
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.tokotlin.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.tokotlin.ui.events.MainStateEvent
import com.hashim.mapswithgeofencing.tokotlin.ui.events.MainStateEvent.*
import com.hashim.mapswithgeofencing.tokotlin.ui.events.MainViewState
import com.hashim.mapswithgeofencing.tokotlin.ui.events.MainViewState.MainFields
import com.hashim.mapswithgeofencing.tokotlin.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        @ApplicationContext private val hContext: Context,
) : ViewModel() {
    private val _hMainStateEvent = MutableLiveData<MainStateEvent>()
    private var hCurrentLocation: Location? = null

    /*Data setter for the view packged into a single object*/
    private val _hMainViewState = MutableLiveData<MainViewState>()
    public val hMainViewState: LiveData<MainViewState>
        get() = _hMainViewState


    val hDataState: LiveData<DataState<MainViewState>> = Transformations
            .switchMap(_hMainStateEvent) {
                it?.let { mainStateEvent ->
                    hHandleStateEvent(mainStateEvent)
                }
            }


    private fun hHandleStateEvent(stateEvent: MainStateEvent): LiveData<DataState<MainViewState>>? {
        when (stateEvent) {
            is OnCurrentLocationFound -> {
                hCurrentLocation = stateEvent.location
                return hCreateMaker(hCurrentLocation)
            }
            is OnMapReady -> {
            }
            is OnCategorySelected -> {
                viewModelScope.launch {
                    /*Todo: get radius from app settings*/
                    hCurrentLocation?.let {
                        hRemoteRepo.hFindNearybyPlaces(
                                category = stateEvent.category,
                                location = it
                        )
                    }
                }
            }
            is None -> {
            }
            else -> {
            }
        }
        return null

    }

    private fun hCreateMaker(location: Location?): LiveData<DataState<MainViewState>>? {
        location?.let { location ->
            val hLatLng = LatLng(location.latitude, location.longitude)
            val hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(hContext, Constants.H_CURRENT_MARKER.toString())

            val hMarkerOptions = MarkerOptions().position(hLatLng)
                    .title(hContext.getString(R.string.you_are_here))
                    .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap))

//            hCurrentMarker.showInfoWindow()

            val mutableLiveData = MutableLiveData<DataState<MainViewState>>()
            mutableLiveData.value = DataState.hData(
                    message = null,
                    data = MainViewState(
                            hMainFields = MainFields(
                                    currentLocation = location,
                                    currentMarkerOptions = hMarkerOptions,
                                    cameraZoom = 12.0F
                            )
                    )
            )
            return mutableLiveData
        }
        return null
    }


    fun hSetStateEvent(mainStateEvent: MainStateEvent) {
        Timber.d("Setting State Event $mainStateEvent")
        _hMainStateEvent.value = mainStateEvent
    }

    fun hSetMainData(it: MainFields) {
        var hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hMainFields = it
        _hMainViewState.value = hUpdate
    }

    fun hGetCurrentViewStateOrNew(): MainViewState {
        val hValue = hMainViewState.value?.let {
            it
        } ?: MainViewState()
        return hValue
    }
}