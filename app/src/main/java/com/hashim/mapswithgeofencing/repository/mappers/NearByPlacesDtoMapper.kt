/*
 * Copyright (c) 2021/  3/ 25.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.mappers

import com.hashim.mapswithgeofencing.Domain.model.NearByPlaces
import com.hashim.mapswithgeofencing.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.network.response.nearybyplaces.NearyByPlacesResultDto

class NearByPlacesDtoMapper : DomainMapper<NearyByPlacesResultDto, NearByPlaces> {
    override fun hMapToDomainModel(model: NearyByPlacesResultDto): NearByPlaces {
        return NearByPlaces(
                userRatingsTotal = model.userRatingsTotal,
                icon = model.icon,
                name = model.name,
                placeId = model.placeId,
                photos = model.photos,
                rating = model.rating,
                types = model.types,
                lat = model.geometry.location.lat,
                lng = model.geometry.location.lng,
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