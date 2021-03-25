/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.forecast


import com.google.gson.annotations.SerializedName

data class ForecastDto(
        @SerializedName("city")
        val city: City,
        @SerializedName("cnt")
        val cnt: Int,
        @SerializedName("cod")
        val cod: String,
        @SerializedName("list")
        val list: List<ForecastList>,
        @SerializedName("message")
        val message: Int
)