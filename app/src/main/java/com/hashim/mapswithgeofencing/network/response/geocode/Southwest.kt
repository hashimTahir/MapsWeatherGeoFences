/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.geocode


import com.google.gson.annotations.SerializedName

data class Southwest(
        @SerializedName("lat")
        val lat: Double,
        @SerializedName("lng")
        val lng: Double
)