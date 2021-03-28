/*
 * Copyright (c) 2021/  3/ 28.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.mappers

import com.hashim.mapswithgeofencing.Domain.model.Directions
import com.hashim.mapswithgeofencing.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.network.response.directions.DirectionsDto

class DirectionDtoMapper : DomainMapper<DirectionsDto, Directions> {
    override fun hMapToDomainModel(model: DirectionsDto): Directions {
        TODO("Not yet implemented")
    }

    override fun hMapFromDomailModel(domainModel: Directions): DirectionsDto {
        TODO("Not yet implemented")
    }
}