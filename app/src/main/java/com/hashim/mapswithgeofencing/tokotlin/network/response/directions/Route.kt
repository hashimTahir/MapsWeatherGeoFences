/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.directions


import com.google.gson.annotations.SerializedName

data class Route(
    @SerializedName("bounds")
    val bounds: Bounds,
    @SerializedName("copyrights")
    val copyrights: String,
    @SerializedName("legs")
    val legs: List<Leg>,
    @SerializedName("overview_polyline")
    val overviewPolyline: OverviewPolyline,
    @SerializedName("summary")
    val summary: String,
    @SerializedName("warnings")
    val warnings: List<Any>,
    @SerializedName("waypoint_order")
    val waypointOrder: List<Any>
)