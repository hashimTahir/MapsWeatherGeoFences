/*
 * Copyright (c) 2021/  4/ 5.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.geocode


import com.google.gson.annotations.SerializedName

data class AddressComponent(
        @SerializedName("long_name")
        val longName: String,
        @SerializedName("short_name")
        val shortName: String,
        @SerializedName("types")
        val types: List<String>
)