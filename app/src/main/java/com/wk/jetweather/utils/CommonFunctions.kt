package com.wk.jetweather.utils

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


    fun getCurrentWeather() : CurrentWeather {
        return CurrentWeather(
            base = "stations",
            clouds = CurrentWeather.Clouds(all = 0),
            cod = 200,
            coord = CurrentWeather.Coord(lat = 33.7695, lon = 72.3611),
            dt = 1726899082,
            id = 1184249,
            main = CurrentWeather.Main(
                feels_like = 35.86,
                grnd_level = 962,
                humidity = 32,
                pressure = 1006,
                sea_level = 1006,
                temp = 35.49,
                temp_max = 35.49,
                temp_min = 35.49
            ),
            name = "Attock",
            sys = CurrentWeather.Sys(
                country = "PK",
                sunrise = 1726880280,
                sunset = 1726924157
            ),
            timezone = 18000,
            visibility = 10000,
            weather = listOf(
                CurrentWeather.Weather(
                    description = "clear sky",
                    icon = "01d",
                    id = 800,
                    main = "Clear"
                )
            ),
            wind = CurrentWeather.Wind(
                deg = 127,
                gust = 1.43,
                speed = 1.4
            )
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