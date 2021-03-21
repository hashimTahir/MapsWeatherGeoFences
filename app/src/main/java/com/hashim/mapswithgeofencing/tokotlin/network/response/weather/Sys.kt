/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.weather


import com.google.gson.annotations.SerializedName


data class Sys(
        @SerializedName("country")
        val country: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("sunrise")
        val sunrise: Int,
        @SerializedName("sunset")
        val sunset: Int,
        @SerializedName("type")
        val type: Int
)