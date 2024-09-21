package com.wk.composeweather.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.composeweather.data.models.currentWeather.CurrentWeather
import com.wk.composeweather.data.repositories.WeatherRepositoryImpl
import com.wk.composeweather.ui.screens.uistate.HomeScreenUiState
import com.wk.composeweather.utils.CommonFunctions
import com.wk.composeweather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherScreenViewModel @Inject constructor(private val weatherRepositoryImpl: WeatherRepositoryImpl) :
    ViewModel() {

    private val _homeScreenUiState =
        MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.InitialState)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()


    init {
        getLocalData()
      //  getCurrentWeather()
    }

    private fun getCurrentWeather(cityName: String = "London") {
        viewModelScope.launch {
            weatherRepositoryImpl.getCurrentWeather(cityName = cityName).collect { response ->
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

  private  fun getLocalData() {
        _homeScreenUiState.update {
            HomeScreenUiState.Success(
                CommonFunctions.getCurrentWeather()
            )
        }
    }
}