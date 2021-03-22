/*
 * Copyright (c) 2021/  3/ 20.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.main

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.CameraUpdateFactory
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
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
        private val hRemoteRepo: RemoteRepo,
        @ApplicationContext private val hContext: Context,
) : ViewModel() {
    private val _hMainStateEvent = MutableLiveData<MainStateEvent>()

    /*Data setter for the view packged into a single object*/
    private val _hMainViewState = MutableLiveData<MainViewState>()
    public val hMainViewState: LiveData<MainViewState>
        get() = _hMainViewState


    val hDataState: LiveData<MainViewState> = Transformations
            .switchMap(_hMainStateEvent) {
                it?.let { mainStateEvent ->
                    hHandleStateEvent(mainStateEvent)
                }
            }


    private fun hHandleStateEvent(stateEvent: MainStateEvent): LiveData<MainViewState> {
        when (stateEvent) {
            is OnCurrentLocationFound -> {
//                hCreateMaker(stateEvent.location)
            }
            is OnMapReady -> {
            }
            is None -> {
            }
            else -> {
            }

        }

    }

    private fun hCreateMaker(location: Location?) {
        location?.let { location ->
            val hLatLng = LatLng(location.latitude, location.longitude)
            val hSmallMarkerBitmap = MarkerUtils.hGetCustomMapMarker(hContext, Constants.H_CURRENT_MARKER.toString())

            MarkerOptions().position(hLatLng)
                    .title(hContext.getString(R.string.you_are_here))
                    .icon(BitmapDescriptorFactory.fromBitmap(hSmallMarkerBitmap))

//            hCurrentMarker.showInfoWindow()

            var newLatLngZoom = CameraUpdateFactory.newLatLngZoom(hLatLng, 12.0f)

        }
    }


    fun hSetStateEvent(mainStateEvent: MainStateEvent) {
        _hMainStateEvent.value = mainStateEvent
    }
}