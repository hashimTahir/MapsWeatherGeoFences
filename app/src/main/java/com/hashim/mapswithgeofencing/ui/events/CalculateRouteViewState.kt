/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.location.Location
import android.os.Parcelable
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CalculateRouteViewState(
        val hCalculateRouteFields: CalculateRouteFields = CalculateRouteFields(),
) : Parcelable {

    @Parcelize
    data class CalculateRouteFields(
            var hTemp: String? = null
    )

}

