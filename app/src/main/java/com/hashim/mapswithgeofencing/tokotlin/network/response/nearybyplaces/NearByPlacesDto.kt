/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.nearybyplaces


import com.google.gson.annotations.SerializedName


data class NearByPlacesDto(
        @SerializedName("html_attributions")
        val htmlAttributions: List<Any>,
        @SerializedName("next_page_token")
        val nextPageToken: String,
        @SerializedName("results")
        val nearyByPlacesResultDtos: List<NearyByPlacesResultDto>,
        @SerializedName("status")
        val status: String
)