package com.wk.weatherwise.ui.screens.forecast.uistate

import com.wk.weatherwise.data.datasource.local.entities.FiveDayForecastEntity

sealed interface ForecastScreenUiState {
    data class Success(val fiveDayFiveDayForecastEntity: List<FiveDayForecastEntity>) : ForecastScreenUiState
    data class Error(val errorMessage: String) : ForecastScreenUiState
    data object Loading : ForecastScreenUiState
    data object InitialState : ForecastScreenUiState
}
