package com.wk.weatherwise.ui.screens.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


open class BaseViewModel : ViewModel() {

    private val _locationPermissionGranted = MutableStateFlow(false)
    val locationPermissionGranted: StateFlow<Boolean> get() = _locationPermissionGranted

    fun updatePermissionStatus(isGranted: Boolean) {
        _locationPermissionGranted.value = isGranted
    }
}