/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.mappers

import com.hashim.mapswithgeofencing.Domain.model.GeoCode
import com.hashim.mapswithgeofencing.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.network.response.geocode.Result

class GeoCodeDtoMapper : DomainMapper<Result, GeoCode> {
    override fun hMapToDomainModel(model: Result): GeoCode {

        return GeoCode(
                formattedAddress = model.formattedAddress,
                location = model.geometry.location,
                placeId = model.placeId,
        )
    }

    override fun hMapFromDomailModel(domainModel: GeoCode): Result {
        TODO("Not yet implemented")
    }

    fun hToDomainList(results: List<Result>): List<GeoCode> {
        return results.map {
            hMapToDomainModel(it)
        }
    }
}