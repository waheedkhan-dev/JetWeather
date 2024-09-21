package com.wk.composeweather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wk.composeweather.ui.screens.HomeScreen
import com.wk.composeweather.ui.screens.WeatherScreenViewModel

@Composable
fun JetWeatherNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = MainScreens.Home.route
    ) {
        composable(route = MainScreens.Home.route) {
            val weatherScreenViewModel: WeatherScreenViewModel = hiltViewModel()
            val homeScreenUiState = weatherScreenViewModel.homeScreenUiState.collectAsStateWithLifecycle().value
            HomeScreen(homeScreenUiState = homeScreenUiState)
        }
    }
}


