package com.wk.jetweather.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast_table")
data class FiveDayForecastEntity(
    @PrimaryKey
    val id : Long,
    val cityId : Int,
    val city : String,
    val icon : String,
    val main : String,
    val description : String,
    val temp : Double,
    val tempMax : Double,
    val tempMin : Double,
)
