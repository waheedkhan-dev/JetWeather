package com.wk.composeweather.ui.navigation

sealed class MainScreens(val route: String) {
    data object Home : MainScreens(route = "home")
}