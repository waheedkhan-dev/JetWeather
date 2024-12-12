package com.wk.jetweather.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.wk.jetweather.data.repositories.DataStoreRepoImpl
import com.wk.jetweather.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val jetWeatherDataStoreRepo: DataStoreRepoImpl) :
    BaseViewModel() {

    private val _isInitialLaunch = MutableStateFlow<Boolean?>(null)
    val isInitialLaunch: StateFlow<Boolean?> get() = _isInitialLaunch

    private val _showCityNameDialog = MutableStateFlow(false)
    val showCityNameDialog: StateFlow<Boolean> = _showCityNameDialog


    init {
        checkInitialLaunch()
    }

    private fun checkInitialLaunch() {
        viewModelScope.launch {
            jetWeatherDataStoreRepo.isInitialLaunch().collect { value ->
                _isInitialLaunch.value = value
            }
        }
    }

    fun setIsInitialLaunch(value: Boolean) {
        viewModelScope.launch {
            jetWeatherDataStoreRepo.setIsInitialLaunch(value)

        }
    }

    fun onCityNameDialogOpen() {
        _showCityNameDialog.value = true
    }


    fun onCityNameDialogDismiss() {
        _showCityNameDialog.value = false
    }

    fun setLastEnteredCityName(cityName: String) {
        viewModelScope.launch {
            jetWeatherDataStoreRepo.setLastEnteredCityName(cityName)
        }
    }

    fun checkLocationPermission(isGranted: Boolean) {
        updatePermissionStatus(isGranted)
    }
}