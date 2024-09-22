package com.wk.jetweather.ui.screens.weather.uistate

import com.wk.jetweather.data.datasource.local.entities.CurrentWeatherEntity

sealed interface HomeScreenUiState {
    data class Success(val currentWeather: CurrentWeatherEntity) : HomeScreenUiState
    data class Error(val errorMessage: String) : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data object InitialState : HomeScreenUiState
}
