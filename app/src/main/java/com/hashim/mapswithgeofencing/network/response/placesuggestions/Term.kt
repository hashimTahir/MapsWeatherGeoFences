/*
 * Copyright (c) 2021/  4/ 12.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.placesuggestions


import com.google.gson.annotations.SerializedName

data class Term(
    @SerializedName("offset")
    val offset: Int,
    @SerializedName("value")
    val value: String
)