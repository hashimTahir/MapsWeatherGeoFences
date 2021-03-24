/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.response.nearybyplaces


import com.google.gson.annotations.SerializedName


data class PlusCode(
        @SerializedName("compound_code")
        val compoundCode: String,
        @SerializedName("global_code")
        val globalCode: String
)