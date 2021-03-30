/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.location.Location
import com.google.android.gms.maps.model.MarkerOptions
import com.hashim.mapswithgeofencing.network.response.directions.Distance
import com.hashim.mapswithgeofencing.network.response.directions.OverviewPolyline
import com.hashim.mapswithgeofencing.network.response.directions.Step

data class CalculateRouteViewState(
        val hCalculateRouteFields: CalculateRouteFields = CalculateRouteFields(),
) {


    data class CalculateRouteFields(
            var hDrawPathVS: DrawPathVS? = null,
            var hSetMapVS: SetMapVS? = null,
    )


    data class DrawPathVS(
            val hOverviewPolyline: OverviewPolyline? = null,
            val hSteps: List<Step>? = null,
            val hDistance: Distance? = null,
            val hEta: String? = null,
            val hDistanceUnit: String? = null
    )

    data class SetMapVS(
            val hDistanceUnit: String? = null,
            val currentLocation: Location,
            val cameraZoom: Float,
            val hMapType: Int? = null
    )

}

