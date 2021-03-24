/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.directions


import com.google.gson.annotations.SerializedName

data class DirectionsResponse(
        @SerializedName("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypoint>,
        @SerializedName("routes")
    val routes: List<Route>,
        @SerializedName("status")
    val status: String
)