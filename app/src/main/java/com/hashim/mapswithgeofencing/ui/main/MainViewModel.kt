/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.main

import android.content.Context
import android.location.Location
import androidx.lifecycle.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.R
import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.repository.remote.RemoteRepo
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent
import com.hashim.mapswithgeofencing.ui.events.MainStateEvent.*
import com.hashim.mapswithgeofencing.ui.events.MainViewState
import com.hashim.mapswithgeofencing.ui.events.MainViewState.MainFields
import com.hashim.mapswithgeofencing.ui.events.MainViewState.NearByFields
import com.hashim.mapswithgeofencing.utils.DataState
import com.hashim.mapswithgeofencing.utils.MarkerUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
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
                return hSubmitCurrentLocationData(hCurrentLocation)
            }
            is OnMapReady -> {
            }
            is OnCategorySelected -> {
                viewModelScope.launch {
                    /*Todo: get radius from app settings*/
                    hCurrentLocation?.let {
                        var hFindNearybyPlaces = hRemoteRepo.hFindNearybyPlaces(
                                category = stateEvent.category,
                                location = it
                        )
                        hSubmitNearByMarkerList(hFindNearybyPlaces, stateEvent.category)
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

    private fun hSubmitNearByMarkerList(nearyByPlacesList: List<NearByPlaces>, category: Category) {
        val hMarkerList = mutableListOf<MarkerOptions>()
        nearyByPlacesList.forEach { place ->
            hMarkerList.add(
                    hCreateMarkerOptions(
                            hLat = place.lat!!,
                            hLng = place.lng!!,
                            hCategory = category
                    )
            )
        }
        _hMainViewState.value = MainViewState(MainFields(), NearByFields(hMarkerList))
    }


    private fun hCreateMarkerOptions(hLat: Double, hLng: Double, hCategory: Category?): MarkerOptions {
        val hLatLng = LatLng(hLat, hLng)
        val hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(hContext, hCategory)


        val hMarkerOptions =
                if (hCategory != null) {
                    MarkerOptions().position(hLatLng)
                            .title(hCategory.name)
                            .snippet(hCategory.name)
                            .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap))
                } else {
                    MarkerOptions().position(hLatLng)
                            .title(hContext.getString(R.string.you_are_here))
                            .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap))
                }


        return hMarkerOptions
    }


    private fun hSubmitCurrentLocationData(location: Location?): LiveData<DataState<MainViewState>>? {
        location?.let { location ->
            val mutableLiveData = MutableLiveData<DataState<MainViewState>>()
            mutableLiveData.value = DataState.hData(
                    message = null,
                    data = MainViewState(
                            hMainFields = MainFields(
                                    currentLocation = location,
                                    currentMarkerOptions = hCreateMarkerOptions(
                                            hLat = location.latitude,
                                            hLng = location.longitude,
                                            hCategory = null
                                    ),
                                    cameraZoom = 12.0F
                            )
                    )
            )
            return mutableLiveData
        }
        return null
    }


    fun hSetStateEvent(mainStateEvent: MainStateEvent) {
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

    fun hSetMarkerData(it: NearByFields) {
        var hUpdate = hGetCurrentViewStateOrNew()
        hUpdate.hNearbyFields = it
        _hMainViewState.value = hUpdate
    }
}