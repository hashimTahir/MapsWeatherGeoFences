/*
 * Copyright (c) 2021/  3/ 23.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.tokotlin.network.model

import com.hashim.mapswithgeofencing.tokotlin.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.tokotlin.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.tokotlin.network.response.nearybyplaces.NearyByPlacesResultDto

class NearByPlacesDtoMapper : DomainMapper<NearyByPlacesResultDto, NearByPlaces> {
    override fun hMapToDomainModel(model: NearyByPlacesResultDto): NearByPlaces {

        return NearByPlaces(
                model.userRatingsTotal,
                model.icon,
                model.name,
                model.placeId,
                model.photos,
                model.rating,
                model.types,
                model.geometry.location.lat,
                model.geometry.location.lat,
        )
    }

    override fun hMapFromDomailModel(domainModel: NearByPlaces): NearyByPlacesResultDto {
        TODO("Not yet implemented")
    }


    fun hToDomainList(list: List<NearyByPlacesResultDto>): List<NearByPlaces> {
        return list.map {
            hMapToDomainModel(it)
        }
    }

    fun hFromDomainList(list: List<NearByPlaces>): List<NearyByPlacesResultDto> {
        return list.map {
            hMapFromDomailModel(it)
        }
    }
}