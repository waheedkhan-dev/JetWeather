package com.wk.composeweather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wk.composeweather.ui.screens.forecast.FiveDayForecast
import com.wk.composeweather.ui.screens.forecast.ForecastViewModel
import com.wk.composeweather.ui.screens.home.HomeScreen
import com.wk.composeweather.ui.screens.home.WeatherScreenViewModel

@Composable
fun JetWeatherNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreens.Home.route
    ) {
        composable(route = MainScreens.Home.route) {
            val weatherScreenViewModel: WeatherScreenViewModel = hiltViewModel()
            val homeScreenUiState =
                weatherScreenViewModel.homeScreenUiState.collectAsStateWithLifecycle().value
            HomeScreen(homeScreenUiState = homeScreenUiState, onFiveDayForecastClick = {
                navController.navigate(MainScreens.FiveDayForecast.route)
            })
        }

        composable(route = MainScreens.FiveDayForecast.route) {
            val forecastViewModel: ForecastViewModel = hiltViewModel()
            FiveDayForecast(onBackClick = {
                navController.popBackStack()
            })
        }
    }
}


