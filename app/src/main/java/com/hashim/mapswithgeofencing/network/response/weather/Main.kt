/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.weather


import com.google.gson.annotations.SerializedName

data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double,
        @SerializedName("humidity")
        val humidity: Int,
        @SerializedName("pressure")
        val pressure: Int,
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("temp_max")
        val tempMax: Int,
        @SerializedName("temp_min")
        val tempMin: Double
)