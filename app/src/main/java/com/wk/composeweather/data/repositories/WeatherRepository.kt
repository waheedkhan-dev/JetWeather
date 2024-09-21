package com.wk.composeweather.data.repositories

import com.wk.composeweather.data.models.currentWeather.CurrentWeather
import com.wk.composeweather.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getCurrentWeather(cityName: String): Flow<Resource<CurrentWeather>>

}