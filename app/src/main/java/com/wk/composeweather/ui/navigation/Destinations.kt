package com.wk.composeweather.ui.navigation

sealed class MainScreens(val route: String) {
    data object Home : MainScreens(route = "home")
    data object FiveDayForecast : MainScreens(route = "five_day_forecast")
}