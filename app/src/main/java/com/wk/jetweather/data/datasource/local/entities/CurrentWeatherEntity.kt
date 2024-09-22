package com.wk.jetweather.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class CurrentWeatherEntity(
    @PrimaryKey
    val id : Int,
    val cityName : String,
    val dt : Long,
    val main : String,
    val description : String,
    val icon : String,
    val feelsLike: Double,
    val grndLevel: Int,
    val humidity: Int,
    val pressure: Int,
    val seaLevel: Int,
    val temp: Double,
    val tempMax: Double,
    val tempMin: Double,
    val deg: Int,
    val gust: Double,
    val speed: Double

)
