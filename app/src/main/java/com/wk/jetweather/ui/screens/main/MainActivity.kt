package com.wk.jetweather.ui.screens.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wk.jetweather.ui.navigation.JetWeatherNavHost
import com.wk.jetweather.ui.screens.base.BaseActivity
import com.wk.jetweather.ui.theme.JetWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onPermissionResult(isGranted: Boolean) {
        viewModel.checkLocationPermission(isGranted)
        if (!isGranted) {
            Toast.makeText(this, "Permission is required for weather updates.", Toast.LENGTH_LONG)
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkPermissionOnLaunch()
        enableEdgeToEdge()
        setContent {
            JetWeatherTheme {
                JetWeatherNavHost(locationPermissionGranted = viewModel.locationPermissionGranted.collectAsStateWithLifecycle().value)
            }
        }
    }

    private fun checkPermissionOnLaunch() {
        val isGranted = checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                android.content.pm.PackageManager.PERMISSION_GRANTED
        viewModel.checkLocationPermission(isGranted)
        if (!isGranted) requestLocationPermission()
    }

}

object Graph {
    const val MAIN = "main_graph"
    const val DETAIL = "detail_graph"
}