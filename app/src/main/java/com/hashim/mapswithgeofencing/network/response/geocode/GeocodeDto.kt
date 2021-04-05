/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.geocode


import com.google.gson.annotations.SerializedName

data class GeocodeDto(
        @SerializedName("plus_code")
        val plusCode: PlusCode,
        @SerializedName("results")
        val results: List<Result>,
        @SerializedName("status")
        val status: String
)