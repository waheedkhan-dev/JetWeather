package com.wk.weatherwise.ui.screens.main

import androidx.lifecycle.viewModelScope
import com.wk.weatherwise.data.repositories.DataStoreRepoImpl
import com.wk.weatherwise.ui.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val jetWeatherDataStoreRepo: DataStoreRepoImpl) :
    BaseViewModel() {

    private val _isInitialLaunch = MutableStateFlow<Boolean?>(null)
    val isInitialLaunch: StateFlow<Boolean?> = _isInitialLaunch.asStateFlow()

    private val _showCityNameDialog = MutableStateFlow(false)
    val showCityNameDialog: StateFlow<Boolean> = _showCityNameDialog.asStateFlow()

    private val _lastEnteredCityName =
        MutableStateFlow(runBlocking { jetWeatherDataStoreRepo.getLastEnteredCityName().first() })
    val lastEnteredCityName: StateFlow<String> = _lastEnteredCityName.asStateFlow()


    init {
        checkInitialLaunch()
    }

   private fun checkInitialLaunch() {
        viewModelScope.launch {

            jetWeatherDataStoreRepo.isInitialLaunch().collect { value ->
                _isInitialLaunch.value = value
            }
        }

        viewModelScope.launch {
            jetWeatherDataStoreRepo.getLastEnteredCityName()
                .collect { value ->
                    _lastEnteredCityName.value = value
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