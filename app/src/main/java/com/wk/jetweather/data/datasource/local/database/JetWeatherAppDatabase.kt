package com.wk.jetweather.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wk.jetweather.data.datasource.local.dao.ForecastDao
import com.wk.jetweather.data.datasource.local.dao.WeatherDao
import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.data.datasource.local.entities.FiveDayForecastEntity
import com.wk.jetweather.utils.Constants.APP_DATABASE


@Database(
    entities = [FiveDayForecastEntity::class,CurrentWeatherEntity::class],
    version = 1,
    exportSchema =  true
)

abstract class JetWeatherAppDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        const val DATABASE_NAME = APP_DATABASE
    }

}
