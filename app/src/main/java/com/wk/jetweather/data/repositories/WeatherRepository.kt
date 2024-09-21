package com.wk.jetweather.data.repositories

import com.wk.jetweather.data.datasource.local.entities.Forecast
import com.wk.jetweather.data.models.currentWeather.CurrentWeather
import com.wk.jetweather.data.models.fiveDayForecast.FiveDayForecast
import com.wk.jetweather.utils.Resource
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun fetchTodayWeather(cityName: String): Flow<Resource<CurrentWeather>>
    suspend fun fetchFiveDayForecast(cityName: String): Flow<Resource<List<Forecast>>>

}