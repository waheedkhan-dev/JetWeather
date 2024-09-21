package com.wk.jetweather.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wk.jetweather.data.datasource.local.entities.Forecast
import kotlinx.coroutines.flow.Flow

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecasts(forecasts: List<Forecast>)

    @Query("SELECT * FROM forecast_table WHERE city = :city")
    fun getForecastByCity(city: String): List<Forecast>


}