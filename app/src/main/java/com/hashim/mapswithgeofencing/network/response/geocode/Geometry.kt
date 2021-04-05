/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.geocode


import com.google.gson.annotations.SerializedName

data class Geometry(
        @SerializedName("bounds")
        val bounds: Bounds,
        @SerializedName("location")
        val location: Location,
        @SerializedName("location_type")
        val locationType: String,
        @SerializedName("viewport")
        val viewport: Viewport
)