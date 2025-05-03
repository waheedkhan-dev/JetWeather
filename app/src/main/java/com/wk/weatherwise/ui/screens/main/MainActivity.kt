package com.wk.weatherwise.ui.screens.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wk.weatherwise.ui.components.CityNameDialog
import com.wk.weatherwise.ui.navigation.JetWeatherNavHost
import com.wk.weatherwise.ui.screens.base.BaseActivity
import com.wk.weatherwise.ui.screens.onboarding.OnboardingScreen
import com.wk.weatherwise.ui.theme.JetWeatherTheme
import com.wk.weatherwise.utils.CommonFunctions.showPermissionDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onPermissionResult(isGranted: Boolean) {
        viewModel.checkLocationPermission(isGranted)
        if (!isGranted) {
            showPermissionDialog(context = this, showCityNameDialog = {
                viewModel.onCityNameDialogOpen()
            })
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
                    if(isInitialLaunch) {
                        OnboardingScreen(onPermissionRequest = {
                            requestLocationPermission()
                        }, onManualSearch = {
                            viewModel.onCityNameDialogOpen()
                        })
                    } else {
                        JetWeatherNavHost()
                       /* if(locationPermissionGranted || lastEnteredCityName.isNotEmpty()) {
                            JetWeatherNavHost()
                        }else{
                            OnboardingScreen(onPermissionRequest = {
                                requestLocationPermission()
                            }, onManualSearch = {
                                viewModel.onCityNameDialogOpen()
                            })
                        }*/

                    }
                }

                if(showCityNameDialog) {
                    CityNameDialog(onDismiss = {
                        viewModel.onCityNameDialogDismiss()
                    }, onCityEntered = {
                        viewModel.setLastEnteredCityName(cityName = it)
                        viewModel.setIsInitialLaunch(false)
                    })
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