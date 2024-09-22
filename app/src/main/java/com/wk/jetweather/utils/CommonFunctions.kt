package com.wk.jetweather.utils

import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.jetweather.data.models.currentWeather.CurrentWeather
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object CommonFunctions {

    fun convertTimestampToUTCFormat(timestamp: Long): String {
        // Convert seconds to milliseconds
        val date = Date(timestamp * 1000)

        // Create a SimpleDateFormat instance for UTC
        val sdf = SimpleDateFormat("E, dd MMMM", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        // Format the date
        return sdf.format(date)
    }

    fun convertTimestampToDayName(timestamp: Long): String {
        // Convert seconds to milliseconds
        val date = Date(timestamp * 1000)

        // Create a SimpleDateFormat instance for the full day name
        val sdf = SimpleDateFormat("EEEE", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        // Format the date to get the full day name
        return sdf.format(date)
    }


    fun getCurrentWeather() : CurrentWeatherEntity {
        return CurrentWeatherEntity(
            dt = 1726899082,
            id = 1184249,
            feelsLike = 35.86,
            grndLevel = 962,
            humidity = 32,
            pressure = 1006,
            seaLevel = 1006,
            temp = 35.49,
            tempMax = 35.49,
            tempMin = 35.49,
            main = "Clear",
            description = "clear sky",
            icon = "01d",
            deg = 127,
            gust = 1.43,
            speed = 1.4,
            cityName = "Attock"
        )
    }

    fun windDirection(degrees: Double): String {
        return when {
            degrees < 22.5 || degrees >= 337.5 -> "N"
            degrees < 67.5 -> "NE"
            degrees < 112.5 -> "E"
            degrees < 157.5 -> "SE"
            degrees < 202.5 -> "S"
            degrees < 247.5 -> "SW"
            degrees < 292.5 -> "W"
            degrees < 337.5 -> "NW"
            else -> "N" // Fallback, should not reach here
        }
    }
}