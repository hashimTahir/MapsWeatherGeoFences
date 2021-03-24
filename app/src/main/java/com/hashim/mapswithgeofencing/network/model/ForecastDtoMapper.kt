/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.model

import com.hashim.mapswithgeofencing.Domain.model.Forecast
import com.hashim.mapswithgeofencing.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.network.response.forecast.ForecastDto
import com.hashim.mapswithgeofencing.network.response.weather.Weather
import com.hashim.mapswithgeofencing.network.response.weather.WeatherDto

class ForecastDtoMapper : DomainMapper<ForecastDto, Forecast> {
    override fun hMapToDomainModel(model: ForecastDto): Forecast {
      return Forecast("")
    }

    override fun hMapFromDomailModel(domainModel: Forecast): ForecastDto {
        TODO("Not yet implemented")
    }

}