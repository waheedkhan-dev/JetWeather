package com.wk.jetweather.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wk.jetweather.data.datasource.local.entities.FiveDayForecastEntity

@Dao
interface ForecastDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertForecasts(fiveDayForecastEntities: List<FiveDayForecastEntity>)

    @Query("SELECT * FROM forecast_table WHERE city = :city")
    fun getForecastByCity(city: String): List<FiveDayForecastEntity>


}