package com.wk.weatherwise.data.repositories

import com.wk.weatherwise.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.weatherwise.data.datasource.local.entities.FiveDayForecastEntity
import com.wk.weatherwise.data.models.currentWeather.CurrentWeather
import com.wk.weatherwise.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun fetchCurrentWeatherByCityName(cityName: String): Flow<Resource<CurrentWeatherEntity>>
    suspend fun fetchCurrentWeatherByLatLon(lat: Double, lon: Double): Flow<Resource<CurrentWeather>>
    suspend fun fetchFiveDayForecast(cityName: String): Flow<Resource<List<FiveDayForecastEntity>>>
    suspend fun getAllWeathersFromDB(): Flow<List<CurrentWeatherEntity>>

}