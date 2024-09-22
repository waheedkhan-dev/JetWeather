package com.wk.jetweather.data.models.fiveDayForecast

import com.wk.jetweather.data.datasource.local.entities.FiveDayForecastEntity
import com.wk.jetweather.utils.CommonFunctions.convertTimestampToUTCFormat
import java.util.Calendar

data class FiveDayForecast(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Days>,
    val message: Int
) {
    data class City(
        val coord: Coord,
        val country: String,
        val id: Int,
        val name: String,
        val population: Int,
        val sunrise: Int,
        val sunset: Int,
        val timezone: Int
    ) {
        data class Coord(
            val lat: Double,
            val lon: Double
        )
    }

    data class Days(
        val clouds: Clouds,
        val dt: Int,
        val dt_txt: String,
        val main: Main,
        val pop: Double,
        val sys: Sys,
        val visibility: Int,
        val weather: List<Weather>,
        val wind: Wind
    ) {
        data class Clouds(
            val all: Int
        )

        data class Main(
            val feels_like: Double,
            val grnd_level: Int,
            val humidity: Int,
            val pressure: Int,
            val sea_level: Int,
            val temp: Double,
            val temp_kf: Double,
            val temp_max: Double,
            val temp_min: Double
        )

        data class Sys(
            val pod: String
        )

        data class Weather(
            val description: String,
            val icon: String,
            val id: Int,
            val main: String
        )

        data class Wind(
            val deg: Int,
            val gust: Double,
            val speed: Double
        )
    }
}

fun FiveDayForecast.toForecastList(): List<FiveDayForecastEntity> {
    // Group the records by day (e.g., Monday, Tuesday, etc.)
    val groupedByDay = this.list.groupBy {
        convertTimestampToUTCFormat(it.dt.toLong())
    }

    // Process each day to find max and min temperatures during the day (6 AM to 6 PM)
    return groupedByDay.mapNotNull { (date, dayRecords) ->
        // Filter the records to include only those within daytime (6 AM to 6 PM)
        val daytimeRecords = dayRecords.filter { forecast ->
            val forecastHour = convertTimestampToHour(forecast.dt.toLong())
            forecastHour in 6..18  // Consider 6 AM to 6 PM as daytime
        }

        // Proceed only if there are daytime records
        if (daytimeRecords.isNotEmpty()) {
            val maxTemp = daytimeRecords.maxOf { it.main.temp_max }
            val minTemp = daytimeRecords.minOf { it.main.temp_min }

            // Take any record from the daytime to get common fields like city and time
            val firstRecord = daytimeRecords.first()

            FiveDayForecastEntity(
                id = firstRecord.dt.toLong(),
                city = this.city.name,
                cityId = this.city.id,
                icon = firstRecord.weather.first().icon,
                main = firstRecord.weather.first().main,
                description = firstRecord.weather.first().description,
                temp = (maxTemp + minTemp) / 2,  // Average of max and min temp for the day
                tempMax = maxTemp,  // Max temp for the day
                tempMin = minTemp   // Min temp for the day
            )
        } else {
            null  // Skip this day if no daytime records exist
        }
    }
}

// Helper function to extract hour from timestamp
fun convertTimestampToHour(timestamp: Long): Int {
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timestamp * 1000  // Convert seconds to milliseconds
    return calendar.get(Calendar.HOUR_OF_DAY)  // Get the hour of the day (0 to 23)
}
