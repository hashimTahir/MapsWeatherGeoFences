/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.directions


import com.google.gson.annotations.SerializedName

data class EndLocationX(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lng")
    val lng: Double
)