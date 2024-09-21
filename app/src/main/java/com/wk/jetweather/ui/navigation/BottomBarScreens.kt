package com.wk.jetweather.ui.navigation

import com.wk.jetweather.R


sealed class BottomBarScreens(var route: String, var title: String, var icon: Int) {
    data object Weather : BottomBarScreens("weather", "Weather", R.drawable.home_icon)
    data object Locations : BottomBarScreens("location", "Location", R.drawable.weather_location)
}