package com.wk.composeweather.data.repositories

import com.wk.composeweather.data.models.currentWeather.CurrentWeather
import com.wk.composeweather.data.models.fiveDayForecast.FiveDayForecast
import com.wk.composeweather.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun fetchTodayWeather(cityName: String): Flow<Resource<CurrentWeather>>
    suspend fun fetchFiveDayForecast(cityName: String): Flow<Resource<FiveDayForecast>>

}