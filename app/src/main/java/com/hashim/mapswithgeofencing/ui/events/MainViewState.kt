/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.network.response.directions.Distance
import com.hashim.mapswithgeofencing.network.response.directions.OverviewPolyline
import com.hashim.mapswithgeofencing.network.response.directions.Step
import kotlinx.android.parcel.Parcelize

data class MainViewState(
        val hMainFields: MainFields = MainFields(),
) {

    data class MainFields(
            var hCurrentLocationVS: CurrentLocationVS? = null,
            var hNearByPlacesVS: NearByPlacesVS? = null,
            var hOnMarkerClickVS: OnMarkerClickVS? = null,
            var hPlaceSuggestionsVS: PlaceSuggestionsVS? = null,
            var hPlaceSelectedVs: PlaceSelectedVS? = null,
    )

    data class PlaceSelectedVS(
            val hDistance: Distance? = null,
            val hOverviewPolyline: OverviewPolyline? = null,
            val hSteps: List<Step>? = null,
            val hDistanceUnit: String? = null,
            val hEta: String? = null,
            val hStartMarker: MarkerOptions? = null,
            val hEndMarker: MarkerOptions? = null,
    )

    data class PlaceSuggestionsVS(
            val hPlaceSuggestionsList: List<String>? = null,
    )


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
            val hPlaceName: String? = null,
            val hAddress: String? = null,
    ) : Parcelable
}

