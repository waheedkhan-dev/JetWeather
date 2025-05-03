package com.wk.weatherwise.ui.screens.weather.uistate

import com.wk.weatherwise.data.datasource.local.entities.CurrentWeatherEntity

sealed interface HomeScreenUiState {
    data class Success(val currentWeather: CurrentWeatherEntity) : HomeScreenUiState
    data class Error(val errorMessage: String) : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data object InitialState : HomeScreenUiState
}
