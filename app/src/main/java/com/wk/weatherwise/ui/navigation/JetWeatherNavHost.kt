package com.wk.weatherwise.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wk.weatherwise.ui.screens.main.Graph

@Composable
fun JetWeatherNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Graph.MAIN
    ) {
        composable(route = Graph.MAIN) { MainScreen() }
    }
}