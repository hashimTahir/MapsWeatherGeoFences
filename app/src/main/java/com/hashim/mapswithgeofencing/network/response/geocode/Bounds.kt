/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.geocode


import com.google.gson.annotations.SerializedName

data class Bounds(
        @SerializedName("northeast")
        val northeast: Northeast,
        @SerializedName("southwest")
        val southwest: Southwest
)