/*
 * Copyright (c) 2021/  3/ 22.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.directions


import com.google.gson.annotations.SerializedName

data class OverviewPolyline(
    @SerializedName("points")
    val points: String
)