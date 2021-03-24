/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.forecast


import com.google.gson.annotations.SerializedName

data class Sys(
    @SerializedName("pod")
    val pod: String
)