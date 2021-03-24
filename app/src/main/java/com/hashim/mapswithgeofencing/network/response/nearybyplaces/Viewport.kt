/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.nearybyplaces


import com.google.gson.annotations.SerializedName


data class Viewport(
        @SerializedName("northeast")
        val northeast: Northeast,
        @SerializedName("southwest")
        val southwest: Southwest
)