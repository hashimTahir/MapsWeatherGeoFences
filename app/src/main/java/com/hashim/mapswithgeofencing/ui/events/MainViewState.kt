/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainViewState(
        val hMainFields: MainFields = MainFields(),
) : Parcelable {

    @Parcelize
    data class MainFields(
            var hCurrentLocationVS: CurrentLocationVS? = null,
            var hNearByPlacesVS: NearByPlacesVS? = null,
            var hOnMarkerClickVS: OnMarkerClickVS? = null,
    ) : Parcelable


    @Parcelize
    data class CurrentLocationVS(
            val currentLocation: Location? = null,
            val currentMarkerOptions: MarkerOptions? = null,
            val cameraZoom: Float? = null,
    ) : Parcelable

    @Parcelize
    data class NearByPlacesVS(
            val hMarkerList: List<MarkerOptions>? = null
    ) : Parcelable


    @Parcelize
    data class OnMarkerClickVS(
            val hTemp: String? = null
    ) : Parcelable
}

