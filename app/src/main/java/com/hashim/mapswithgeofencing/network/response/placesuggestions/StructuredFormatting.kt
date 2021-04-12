/*
 * Copyright (c) 2021/  4/ 12.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.placesuggestions


import com.google.gson.annotations.SerializedName

data class StructuredFormatting(
    @SerializedName("main_text")
    val mainText: String,
    @SerializedName("main_text_matched_substrings")
    val mainTextMatchedSubstrings: List<MainTextMatchedSubstring>,
    @SerializedName("secondary_text")
    val secondaryText: String
)