package com.wk.weatherwise.ui.navigation

import android.app.Activity.RESULT_OK
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.wk.weatherwise.ui.screens.main.Graph
import com.wk.weatherwise.ui.screens.cityweather.CityWeatherScreen
import com.wk.weatherwise.ui.screens.cityweather.CityWeatherScreenViewModel
import com.wk.weatherwise.ui.screens.forecast.FiveDayForecast
import com.wk.weatherwise.ui.screens.forecast.ForecastViewModel
import com.wk.weatherwise.ui.screens.weather.CurrentWeatherScreen
import com.wk.weatherwise.ui.screens.weather.CurrentWeatherScreenViewModel
import com.wk.weatherwise.utils.CommonFunctions.showEnableLocationDialog

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

            val showEnableLocationDialog = rememberSaveable { mutableStateOf(false) }
            val currentWeatherScreenViewModel: CurrentWeatherScreenViewModel = hiltViewModel()
            val lastEnteredCityName = currentWeatherScreenViewModel.lastEnteredCityName
            val lastLat = currentWeatherScreenViewModel.lastLat
            val lastLon = currentWeatherScreenViewModel.lastLon
            val context = navHostController.context

            val locationPermissionGranted =
                context.checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                        android.content.pm.PackageManager.PERMISSION_GRANTED

            val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
                ActivityResultContracts.StartIntentSenderForResult()
            ) { activityResult ->
                if (activityResult.resultCode == RESULT_OK) {
                    currentWeatherScreenViewModel.fetchCurrentWeather()
                } else {
                    if (lastLat != 0.0 && lastLon != 0.0) {
                        currentWeatherScreenViewModel.fetchCurrentWeatherByLatLon(lastLat, lastLon)
                    } else if (lastEnteredCityName.isNotEmpty()) {
                        currentWeatherScreenViewModel.fetchCurrentWeatherByCityName(
                            lastEnteredCityName
                        )
                    } else {
                        // show information dialog why location is required
                        showEnableLocationDialog.value = true
                    }

                }
            }


            val isLocationEnabled =
                currentWeatherScreenViewModel.isLocationEnabled.collectAsState().value


            LaunchedEffect(key1 = isLocationEnabled, key2 = locationPermissionGranted) {
                if (isLocationEnabled && locationPermissionGranted) {
                    currentWeatherScreenViewModel.fetchCurrentWeather()
                } else if (lastLat != 0.0 && lastLon != 0.0) {
                    currentWeatherScreenViewModel.fetchCurrentWeatherByLatLon(lastLat, lastLon)
                } else if (lastEnteredCityName.isNotEmpty()) {
                    currentWeatherScreenViewModel.fetchCurrentWeatherByCityName(
                        lastEnteredCityName
                    )
                } else {
                    currentWeatherScreenViewModel.enableLocationRequest(context) {
                        requestLocationPermissionLauncher.launch(it)
                    }
                }
            }

            val homeScreenUiState =
                currentWeatherScreenViewModel.homeScreenUiState.collectAsStateWithLifecycle().value

            CurrentWeatherScreen(
                homeScreenUiState = homeScreenUiState,
                onFiveDayForecastClick = {
                    navHostController.navigate(DetailDestinations.FiveDayForecast.route)
                }
            )

            if (showEnableLocationDialog.value) {
                showEnableLocationDialog(context, enableLocationRequest = {
                    currentWeatherScreenViewModel.enableLocationRequest(context) {
                        requestLocationPermissionLauncher.launch(it)
                        showEnableLocationDialog.value = false
                    }
                })
            }

        }

        composable(route = BottomBarScreens.Locations.route) {
            val cityWeatherScreenViewModel: CityWeatherScreenViewModel = hiltViewModel()
            val allWeathers =
                cityWeatherScreenViewModel.allWeathers.collectAsStateWithLifecycle().value
            CityWeatherScreen(allWeathers = allWeathers, onSearchAction = {
                cityWeatherScreenViewModel.searchWeather(it)
            }, onCardClick = {
                navHostController.navigate(DetailDestinations.FiveDayForecast.route.plus("?cityName=$it"))
            })
        }

    }
}


fun NavGraphBuilder.detailNavGraph(navHostController: NavHostController) {
    navigation(
        route = Graph.DETAIL,
        startDestination = DetailDestinations.FiveDayForecast.route
    ) {
        composable(route = DetailDestinations.FiveDayForecast.route.plus("?cityName={cityName}"), arguments = listOf(
            navArgument("cityName") {
                type = NavType.StringType
                nullable = true
                defaultValue = null
            }
        )) { backStackEntry ->
            val forecastViewModel: ForecastViewModel = hiltViewModel()
            val cityName = backStackEntry.arguments?.getString("cityName")
                ?: forecastViewModel.cityName // fallback or default

            LaunchedEffect(Unit) {
                forecastViewModel.fetchFiveDayForecast(cityName = cityName)
            }

            val forecastUiState =
                forecastViewModel.forecastUiState.collectAsStateWithLifecycle().value

            FiveDayForecast(forecastUiState = forecastUiState, onBackClick = {
                navHostController.popBackStack()
            })
        }
    }
}



