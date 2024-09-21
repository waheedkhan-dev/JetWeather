package com.wk.jetweather.data.datasource.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wk.jetweather.data.datasource.local.dao.ForecastDao
import com.wk.jetweather.data.datasource.local.entities.Forecast
import com.wk.jetweather.utils.Constants.APP_DATABASE


@Database(
    entities = [Forecast::class],
    version = 1,
    exportSchema =  true
)

abstract class JetWeatherAppDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao

    companion object {
        const val DATABASE_NAME = APP_DATABASE
    }

}
