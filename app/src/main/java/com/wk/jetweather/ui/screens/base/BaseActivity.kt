package com.wk.jetweather.ui.screens.base

import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts

abstract class BaseActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            onPermissionResult(isGranted)
        }

    abstract fun onPermissionResult(isGranted: Boolean)

    fun requestLocationPermission() {
        requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
    }
}