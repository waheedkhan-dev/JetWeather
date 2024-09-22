package com.wk.jetweather.utils.previewers

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity

class CurrentWeatherProvider : PreviewParameterProvider<CurrentWeatherEntity> {
    override val values: Sequence<CurrentWeatherEntity>
        get() = sequenceOf(
            CurrentWeatherEntity(
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
        )
}