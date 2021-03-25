/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.weather


import com.google.gson.annotations.SerializedName

data class Clouds(
        @SerializedName("all")
        val all: Int
)