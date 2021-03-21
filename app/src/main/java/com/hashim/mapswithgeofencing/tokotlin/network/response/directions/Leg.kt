/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.directions


import com.google.gson.annotations.SerializedName

data class Leg(
    @SerializedName("distance")
    val distance: Distance,
    @SerializedName("duration")
    val duration: Duration,
    @SerializedName("end_address")
    val endAddress: String,
    @SerializedName("end_location")
    val endLocation: EndLocation,
    @SerializedName("start_address")
    val startAddress: String,
    @SerializedName("start_location")
    val startLocation: StartLocation,
    @SerializedName("steps")
    val steps: List<Step>,
    @SerializedName("traffic_speed_entry")
    val trafficSpeedEntry: List<Any>,
    @SerializedName("via_waypoint")
    val viaWaypoint: List<Any>
)