package com.wk.jetweather.data.datasource.remote

import com.wk.jetweather.data.models.currentWeather.CurrentWeather
import com.wk.jetweather.data.models.fiveDayForecast.FiveDayForecast
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JetWeatherApi {
    @GET("weather")
    suspend fun fetchTodayWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric") : Response<CurrentWeather>

    @GET("forecast")
    suspend fun fetchFiveDayForecast(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric") : Response<FiveDayForecast>
}