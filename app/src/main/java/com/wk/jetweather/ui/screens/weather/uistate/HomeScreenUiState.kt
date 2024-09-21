package com.wk.jetweather.ui.screens.weather.uistate

import com.wk.jetweather.data.models.currentWeather.CurrentWeather

sealed interface HomeScreenUiState {
    data class Success(val currentWeather: CurrentWeather) : HomeScreenUiState
    data class Error(val errorMessage: String) : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data object InitialState : HomeScreenUiState
}
