package com.wk.jetweather.ui.screens.main

import com.wk.jetweather.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel  @Inject constructor() : BaseViewModel() {
    fun checkLocationPermission(isGranted: Boolean) {
        updatePermissionStatus(isGranted)
    }
}