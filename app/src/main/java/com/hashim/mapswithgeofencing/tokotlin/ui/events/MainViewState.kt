/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.ui.events

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MainViewState(
        var hMainFields: MainFields = MainFields(),
        var hNearbyFields: NearByFields = NearByFields(),
) : Parcelable {

    @Parcelize
    data class MainFields(
            var currentLocation: Location? = null,
            var currentMarkerOptions: MarkerOptions? = null,
            var cameraZoom: Float? = null,
    ) : Parcelable


    @Parcelize
    data class NearByFields(
            val hMarkerList: List<MarkerOptions>? = null
    ) : Parcelable
}

