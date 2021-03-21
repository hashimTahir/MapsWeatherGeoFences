/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.nearybyplaces


import com.google.gson.annotations.SerializedName


data class Geometry(
        @SerializedName("location")
        val location: Location,
        @SerializedName("viewport")
        val viewport: Viewport
)