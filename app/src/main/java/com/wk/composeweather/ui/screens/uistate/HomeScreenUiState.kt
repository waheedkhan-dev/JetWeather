package com.wk.composeweather.ui.screens.uistate

import com.wk.composeweather.data.models.currentWeather.CurrentWeather

sealed interface HomeScreenUiState {
    data class Success(val currentWeather: CurrentWeather) : HomeScreenUiState
    data class Error(val errorMessage: String) : HomeScreenUiState
    data object Loading : HomeScreenUiState
    data object InitialState : HomeScreenUiState
}
