package com.wk.weatherwise.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wk.weatherwise.data.datasource.local.entities.CurrentWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeather(weather: CurrentWeatherEntity)

    @Query("SELECT * FROM weather WHERE cityName = :city")
    fun getCurrentWeatherByCity(city: String): CurrentWeatherEntity

    @Query("SELECT *FROM weather ORDER BY dt DESC")
    fun getAllWeather() : Flow<List<CurrentWeatherEntity>>


}