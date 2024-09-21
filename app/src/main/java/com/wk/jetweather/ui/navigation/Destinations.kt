package com.wk.jetweather.ui.navigation


sealed class DetailDestinations(val route: String) {
    data object FiveDayForecast : DetailDestinations(route = "five_day_forecast")
}