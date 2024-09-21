package com.wk.jetweather.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentSelectedScreen by navController.currentScreenAsState()
    val currentRoute by navController.currentRouteAsState()

    val bottomBarScreens = listOf(
        BottomBarScreens.Weather,
        BottomBarScreens.Locations,
    )
    val bottomBarDestination = bottomBarScreens.any { it.route == currentRoute }
    Scaffold(modifier = modifier.fillMaxSize(),
        containerColor = Color.White,
        bottomBar = { if (bottomBarDestination) {
            NavigationBar(
                windowInsets = WindowInsets.navigationBars,
                containerColor = NavigationBarDefaults.containerColor,
                tonalElevation = NavigationBarDefaults.Elevation
            ) {

                bottomBarScreens.forEachIndexed { _, screen ->
                    NavigationBarItem(
                        label = {
                            Text(
                                text = screen.title, style = TextStyle(
                                    fontWeight = FontWeight.Normal
                                )
                            )
                        },

                        icon = {
                            Icon(
                                modifier = modifier.size(24.dp),
                                painter = painterResource(id = screen.icon),
                                contentDescription = screen.route
                            )
                        },

                        selected = currentSelectedScreen == screen,

                        alwaysShowLabel = true,

                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigateToRootScreen(screen)
                            }
                        }
                    )
                }
            }
        } }
    ) { padding->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()

        ) {
            MainGraph(navHostController = navController)
        }
    }

}


@Stable
@Composable
private fun NavController.currentScreenAsState(): State<BottomBarScreens> {
    val selectedItem = remember { mutableStateOf<BottomBarScreens>(BottomBarScreens.Weather) }
    DisposableEffect(key1 = this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            when {
                destination.hierarchy.any { it.route == BottomBarScreens.Weather.route } -> {
                    selectedItem.value = BottomBarScreens.Weather
                }

                destination.hierarchy.any { it.route == BottomBarScreens.Locations.route } -> {
                    selectedItem.value = BottomBarScreens.Locations
                }
            }

        }
        addOnDestinationChangedListener(listener)
        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}

@Stable
@Composable
private fun NavController.currentRouteAsState(): State<String?> {
    val selectedItem = remember { mutableStateOf<String?>(null) }
    DisposableEffect(this) {
        val listener = NavController.OnDestinationChangedListener { _, destination, _ ->
            selectedItem.value = destination.route
        }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }
    return selectedItem
}

private fun NavController.navigateToRootScreen(bottomBarScreens: BottomBarScreens) {
    navigate(bottomBarScreens.route) {
        launchSingleTop = true
        restoreState = true
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
    }
}