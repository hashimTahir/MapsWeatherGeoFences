/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.network.model

import com.hashim.mapswithgeofencing.Domain.model.Weather
import com.hashim.mapswithgeofencing.Domain.util.DomainMapper
import com.hashim.mapswithgeofencing.network.response.weather.WeatherDto

class WeatherDtoMapper : DomainMapper<WeatherDto, Weather> {
    override fun hMapToDomainModel(model: WeatherDto): Weather {
        return Weather(
                model.coord.lat,
                model.coord.lon,
                model.weather.get(0).description,
                model.weather.get(0).icon,
                model.weather.get(0).main,
                model.main.temp,
                model.main.tempMax,
                model.main.tempMin,
                model.main.feelsLike,
                model.main.humidity,
                model.main.pressure,
                model.wind.speed,
                model.sys.sunrise,
                model.sys.sunset,
        )
    }

    override fun hMapFromDomailModel(domainModel: Weather): WeatherDto {
        TODO("Not yet implemented")
    }
}