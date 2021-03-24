/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.nearybyplaces


import com.google.gson.annotations.SerializedName


data class Photo(
        @SerializedName("height")
        val height: Int,
        @SerializedName("html_attributions")
        val htmlAttributions: List<String>,
        @SerializedName("photo_reference")
        val photoReference: String,
        @SerializedName("width")
        val width: Int
)