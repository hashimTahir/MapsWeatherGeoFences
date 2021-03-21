/*
 * Copyright (c) 2021/  3/ 21.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.response.nearybyplaces


import com.google.gson.annotations.SerializedName


data class Result(
        @SerializedName("business_status")
        val businessStatus: String,
        @SerializedName("geometry")
        val geometry: Geometry,
        @SerializedName("icon")
        val icon: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("opening_hours")
        val openingHours: OpeningHours,
        @SerializedName("permanently_closed")
        val permanentlyClosed: Boolean,
        @SerializedName("photos")
        val photos: List<Photo>,
        @SerializedName("place_id")
        val placeId: String,
        @SerializedName("plus_code")
        val plusCode: PlusCode,
        @SerializedName("price_level")
        val priceLevel: Int,
        @SerializedName("rating")
        val rating: Double,
        @SerializedName("reference")
        val reference: String,
        @SerializedName("scope")
        val scope: String,
        @SerializedName("types")
        val types: List<String>,
        @SerializedName("user_ratings_total")
        val userRatingsTotal: Int,
        @SerializedName("vicinity")
        val vicinity: String
)