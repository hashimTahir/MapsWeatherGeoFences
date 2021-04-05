/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.geocode


import com.google.gson.annotations.SerializedName

data class Viewport(
        @SerializedName("northeast")
        val northeast: NortheastX,
        @SerializedName("southwest")
        val southwest: SouthwestX
)