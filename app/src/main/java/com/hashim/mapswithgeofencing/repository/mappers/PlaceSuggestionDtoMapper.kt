/*
 * Copyright (c) 2021/  4/ 12.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.repository.mappers

import com.hashim.mapswithgeofencing.Domain.model.PlaceSuggestions
import com.hashim.mapswithgeofencing.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.network.response.placesuggestions.Prediction

class PlaceSuggestionDtoMapper : DomainMapper<Prediction, PlaceSuggestions> {
    override fun hMapToDomainModel(model: Prediction): PlaceSuggestions {
        return PlaceSuggestions(
                model.description,
                model.placeId,
        )
    }

    override fun hMapFromDomailModel(domainModel: PlaceSuggestions): Prediction {
        TODO("Not yet implemented")
    }

    fun hToDomainList(predications: List<Prediction>): List<PlaceSuggestions> {
        return predications.map {
            hMapToDomainModel(it)
        }
    }


}