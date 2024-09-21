package com.wk.composeweather.data.datasource.remote

import com.wk.composeweather.data.models.currentWeather.CurrentWeather
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JetWeatherApi {
    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String) : Response<CurrentWeather>
}