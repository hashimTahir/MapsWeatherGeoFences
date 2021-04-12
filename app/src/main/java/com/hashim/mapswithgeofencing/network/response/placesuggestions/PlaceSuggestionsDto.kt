/*
 * Copyright (c) 2021/  4/ 12.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.placesuggestions


import com.google.gson.annotations.SerializedName

data class PlaceSuggestionsDto(
    @SerializedName("predictions")
    val predictions: List<Prediction>,
    @SerializedName("status")
    val status: String
)