package com.wk.jetweather.ui.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wk.jetweather.ui.components.CityNameDialog
import com.wk.jetweather.ui.navigation.JetWeatherNavHost
import com.wk.jetweather.ui.screens.base.BaseActivity
import com.wk.jetweather.ui.screens.onboarding.OnboardingScreen
import com.wk.jetweather.ui.theme.JetWeatherTheme
import com.wk.jetweather.utils.CommonFunctions.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onPermissionResult(isGranted: Boolean) {
        viewModel.checkLocationPermission(isGranted)
        if (!isGranted) {
            if (viewModel.lastEnteredCityName.value.isEmpty()) {
                showPermissionDialog(context = this, showCityNameDialog = {
                    viewModel.onCityNameDialogOpen()
                })
            }

        } else {
            viewModel.setIsInitialLaunch(false)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissionOnLaunch()
        enableEdgeToEdge()
        setContent {
            JetWeatherTheme {

                val isInitialLaunch = viewModel.isInitialLaunch.collectAsStateWithLifecycle().value

                val locationPermissionGranted =
                    viewModel.locationPermissionGranted.collectAsStateWithLifecycle().value
                val lastEnteredCityName =
                    viewModel.lastEnteredCityName.collectAsStateWithLifecycle().value
                val showCityNameDialog =
                    viewModel.showCityNameDialog.collectAsStateWithLifecycle().value

                isInitialLaunch?.let {
                    when {
                        isInitialLaunch -> {
                            OnboardingScreen(onPermissionRequest = {
                                requestLocationPermission()
                            }, onManualSearch = {
                                viewModel.onCityNameDialogOpen()
                            })
                        }

                        locationPermissionGranted || lastEnteredCityName.isNotEmpty() -> {
                            JetWeatherNavHost()
                        }

                        showCityNameDialog -> {
                            CityNameDialog(onDismiss = {
                                viewModel.onCityNameDialogDismiss()
                            }, onCityEntered = {
                                viewModel.setLastEnteredCityName(cityName = it)
                                if (isInitialLaunch) {
                                    viewModel.setIsInitialLaunch(false)
                                }
                            })
                        }

                        lastEnteredCityName.isEmpty() -> {
                            requestLocationPermission()
                        }
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        checkPermissionOnLaunch()
    }

    private fun checkPermissionOnLaunch() {
        val isGranted = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
        viewModel.checkLocationPermission(isGranted)
        if (isGranted) viewModel.setIsInitialLaunch(false)

    }
}


object Graph {
    const val MAIN = "main_graph"
    const val DETAIL = "detail_graph"
}