/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.forecast


import com.google.gson.annotations.SerializedName

data class ForecastList(
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("dt_txt")
        val dtTxt: String,
        @SerializedName("main")
        val main: Main,
        @SerializedName("pop")
        val pop: Int,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("visibility")
        val visibility: Int,
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind")
        val wind: Wind
)