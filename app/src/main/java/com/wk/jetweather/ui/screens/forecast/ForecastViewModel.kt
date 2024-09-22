package com.wk.jetweather.ui.screens.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.jetweather.data.repositories.WeatherRepositoryImpl
import com.wk.jetweather.ui.screens.forecast.uistate.ForecastScreenUiState
import com.wk.jetweather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ForecastViewModel @Inject constructor(
    @Named("defaultCityName") private val defaultCity: String,
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ViewModel() {

    private val _forecastUiState =
        MutableStateFlow<ForecastScreenUiState>(ForecastScreenUiState.InitialState)

    val forecastUiState : StateFlow<ForecastScreenUiState> = _forecastUiState.asStateFlow()


    init {
        fetchFiveDayForecast()
    }

    private fun fetchFiveDayForecast() {
        viewModelScope.launch {
            weatherRepositoryImpl.fetchFiveDayForecast(cityName = defaultCity).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _forecastUiState.update {
                            ForecastScreenUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        _forecastUiState.update {
                            ForecastScreenUiState.Success(fiveDayFiveDayForecastEntity = response.data)
                        }
                    }

                    is Resource.Error -> {
                        _forecastUiState.update {
                            ForecastScreenUiState.Error(errorMessage = response.message)
                        }
                    }
                }

            }
        }
    }
}