/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.weather


import com.google.gson.annotations.SerializedName

data class Weather(
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("main")
        val main: String
)