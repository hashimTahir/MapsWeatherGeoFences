/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.forecast


import com.google.gson.annotations.SerializedName

data class ForecastResponse(
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

