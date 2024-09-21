package com.wk.jetweather.data.models.fiveDayForecast

import com.wk.jetweather.data.datasource.local.entities.Forecast
import com.wk.jetweather.utils.CommonFunctions.convertTimestampToUTCFormat

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

//Extension function to convert FiveDayForecast to List of Forecast
fun FiveDayForecast.toForecastList(): List<Forecast> {
    // Group the records by day (e.g., Monday, Tuesday, etc.)
    val groupedByDay = this.list.groupBy {
        convertTimestampToUTCFormat(it.dt.toLong())
    }

    // Process each day to find max and min temperatures
    return groupedByDay.map { (date, dayRecords) ->
        val maxTemp = dayRecords.maxOf { it.main.temp_max }
        val minTemp = dayRecords.minOf { it.main.temp_min }

        // Take any record from the day to get common fields like city and time
        val firstRecord = dayRecords.first()

        Forecast(
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
    }
}
