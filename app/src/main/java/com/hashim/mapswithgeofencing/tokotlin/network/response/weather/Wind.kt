/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.weather


import com.google.gson.annotations.SerializedName


data class Wind(
        @SerializedName("deg")
        val deg: Int,
        @SerializedName("speed")
        val speed: Double
)