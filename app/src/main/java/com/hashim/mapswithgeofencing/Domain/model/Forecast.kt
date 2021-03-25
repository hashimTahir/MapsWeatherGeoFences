/*
 * Copyright (c) 2021/  3/ 24.  Created by Hashim Tahir
 */

package com.hashim.mapswithgeofencing.Domain.model

import com.hashim.mapswithgeofencing.network.response.forecast.City
import com.hashim.mapswithgeofencing.network.response.forecast.ForecastList

data class Forecast(val city: City, val list: List<ForecastList>)
