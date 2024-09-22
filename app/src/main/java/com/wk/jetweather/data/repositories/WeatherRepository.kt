package com.wk.jetweather.data.repositories

import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.data.datasource.local.entities.FiveDayForecastEntity
import com.wk.jetweather.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun fetchTodayWeather(cityName: String): Flow<Resource<CurrentWeatherEntity>>
    suspend fun fetchFiveDayForecast(cityName: String): Flow<Resource<List<FiveDayForecastEntity>>>
    suspend fun getAllWeathersFromDB(): Flow<List<CurrentWeatherEntity>>

}