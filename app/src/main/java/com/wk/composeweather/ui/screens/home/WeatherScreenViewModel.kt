package com.wk.composeweather.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.composeweather.data.repositories.WeatherRepositoryImpl
import com.wk.composeweather.ui.screens.home.uistate.HomeScreenUiState
import com.wk.composeweather.utils.CommonFunctions
import com.wk.composeweather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(
    @Named("defaultCityName") private val defaultCity: String,
    private val weatherRepositoryImpl: WeatherRepositoryImpl) :
    ViewModel() {

    private val _homeScreenUiState =
        MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.InitialState)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()


    init {
        getLocalData()
      //  fetchTodayWeather()
    }

    private fun fetchTodayWeather(cityName: String = defaultCity) {
        viewModelScope.launch {
            weatherRepositoryImpl.fetchTodayWeather(cityName = cityName).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _homeScreenUiState.update {
                            HomeScreenUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        _homeScreenUiState.update {
                            HomeScreenUiState.Success(response.data)
                        }
                    }

                    is Resource.Error -> {
                        _homeScreenUiState.update {
                            HomeScreenUiState.Error(response.message)
                        }
                    }
                }
            }

        }
    }

    /*private fun fetchFiveDayForecast(cityName: String = defaultCity) {
        viewModelScope.launch {
            weatherRepositoryImpl.fetchFiveDayForecast(cityName = cityName).collect { response ->
                when (response) {
                    is Resource.Loading -> {
                        _homeScreenUiState.update {
                            HomeScreenUiState.Loading
                        }
                    }

                    is Resource.Success -> {
                        _homeScreenUiState.update {
                            HomeScreenUiState.Success(response.data)
                        }
                    }

                    is Resource.Error -> {
                        _homeScreenUiState.update {
                            HomeScreenUiState.Error(response.message)
                        }
                    }
                }
            }

        }
    }*/

  private  fun getLocalData() {
        _homeScreenUiState.update {
            HomeScreenUiState.Success(
                CommonFunctions.getCurrentWeather()
            )
        }
    }
}