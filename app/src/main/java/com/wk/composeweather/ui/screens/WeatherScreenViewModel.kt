package com.wk.composeweather.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.composeweather.data.models.currentWeather.CurrentWeather
import com.wk.composeweather.data.repositories.WeatherRepositoryImpl
import com.wk.composeweather.ui.screens.uistate.HomeScreenUiState
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
                CurrentWeather(
                    base = "stations",
                    clouds = CurrentWeather.Clouds(all = 0),
                    cod = 200,
                    coord = CurrentWeather.Coord(lat = 33.7695, lon = 72.3611),
                    dt = 1726899082,
                    id = 1184249,
                    main = CurrentWeather.Main(
                        feels_like = 307.46,
                        grnd_level = 964,
                        humidity = 40,
                        pressure = 1007,
                        sea_level = 1007,
                        temp = 306.48,
                        temp_max = 306.48,
                        temp_min = 306.48
                    ),
                    name = "Attock",
                    sys = CurrentWeather.Sys(
                        country = "PK",
                        sunrise = 1726880280,
                        sunset = 1726924157
                    ),
                    timezone = 18000,
                    visibility = 10000,
                    weather = listOf(
                        CurrentWeather.Weather(
                            description = "clear sky",
                            icon = "01d",
                            id = 800,
                            main = "Clear"
                        )
                    ),
                    wind = CurrentWeather.Wind(
                        deg = 127,
                        gust = 1.43,
                        speed = 1.4
                    )
                )
            )
        }
    }
}