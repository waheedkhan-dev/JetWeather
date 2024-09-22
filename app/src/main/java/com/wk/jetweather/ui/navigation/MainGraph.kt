package com.wk.jetweather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.wk.jetweather.ui.screens.Graph
import com.wk.jetweather.ui.screens.cityweather.CityWeatherScreen
import com.wk.jetweather.ui.screens.cityweather.CityWeatherScreenViewModel
import com.wk.jetweather.ui.screens.forecast.FiveDayForecast
import com.wk.jetweather.ui.screens.forecast.ForecastViewModel
import com.wk.jetweather.ui.screens.weather.CurrentWeatherScreen
import com.wk.jetweather.ui.screens.weather.CurrentWeatherScreenViewModel

@Composable
fun MainGraph(modifier: Modifier = Modifier, navHostController: NavHostController) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = Graph.MAIN
    ) {
        bottomNavGraph(navHostController = navHostController)
        detailNavGraph(navHostController = navHostController)
    }
}

fun NavGraphBuilder.bottomNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.MAIN,
        startDestination = BottomBarScreens.Weather.route
    ) {
        composable(route = BottomBarScreens.Weather.route) {
            val currentWeatherScreenViewModel: CurrentWeatherScreenViewModel = hiltViewModel()
            LaunchedEffect(Unit) {
                currentWeatherScreenViewModel.fetchTodayWeather()
            }
            val homeScreenUiState =
                currentWeatherScreenViewModel.homeScreenUiState.collectAsStateWithLifecycle().value
            CurrentWeatherScreen(homeScreenUiState = homeScreenUiState, onFiveDayForecastClick = {
                navHostController.navigate(DetailDestinations.FiveDayForecast.route)
            })
        }

        composable(route = BottomBarScreens.Locations.route) {
            val cityWeatherScreenViewModel: CityWeatherScreenViewModel = hiltViewModel()
            val allWeathers =
                cityWeatherScreenViewModel.allWeathers.collectAsStateWithLifecycle().value
            CityWeatherScreen(allWeathers = allWeathers, onSearchAction = {
                cityWeatherScreenViewModel.searchWeather(it)
            })
        }

    }
}


fun NavGraphBuilder.detailNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.DETAIL,
        startDestination = DetailDestinations.FiveDayForecast.route
    ) {
        composable(route = DetailDestinations.FiveDayForecast.route) {
            val forecastViewModel: ForecastViewModel = hiltViewModel()
            val forecastUiState =
                forecastViewModel.forecastUiState.collectAsStateWithLifecycle().value
            FiveDayForecast(forecastUiState = forecastUiState, onBackClick = {
                navHostController.popBackStack()
            })
        }
    }
}



