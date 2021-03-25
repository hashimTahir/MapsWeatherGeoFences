/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.forecast


import com.google.gson.annotations.SerializedName

data class Wind(
        @SerializedName("deg")
        val deg: Int,
        @SerializedName("speed")
        val speed: Double
)