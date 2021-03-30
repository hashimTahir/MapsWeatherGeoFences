/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.ui.events

import android.os.Parcelable
import com.hashim.mapswithgeofencing.network.response.directions.Distance
import com.hashim.mapswithgeofencing.network.response.directions.OverviewPolyline
import com.hashim.mapswithgeofencing.network.response.directions.Step
import kotlinx.android.parcel.Parcelize

data class CalculateRouteViewState(
        val hCalculateRouteFields: CalculateRouteFields = CalculateRouteFields(),
)  {


    data class CalculateRouteFields(
            var hDrawPathVS: DrawPathVS? = null,
    )


    data class DrawPathVS(
            val hOverviewPolyline: OverviewPolyline? = null,
            val hSteps: List<Step>? = null,
            val hDistance: Distance? = null,
    )

}

