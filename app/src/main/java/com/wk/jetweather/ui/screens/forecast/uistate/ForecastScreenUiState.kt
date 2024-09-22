package com.wk.jetweather.ui.screens.forecast.uistate

import com.wk.jetweather.data.datasource.local.entities.FiveDayForecastEntity

sealed interface ForecastScreenUiState {
    data class Success(val fiveDayFiveDayForecastEntity: List<FiveDayForecastEntity>) : ForecastScreenUiState
    data class Error(val errorMessage: String) : ForecastScreenUiState
    data object Loading : ForecastScreenUiState
    data object InitialState : ForecastScreenUiState
}
