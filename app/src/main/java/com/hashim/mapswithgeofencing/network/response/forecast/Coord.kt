/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.forecast


import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)