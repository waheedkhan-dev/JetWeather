package com.wk.weatherwise.ui.screens.cityweather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.weatherwise.data.datasource.local.entities.CurrentWeatherEntity
import com.wk.weatherwise.data.repositories.WeatherRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CityWeatherScreenViewModel @Inject constructor(private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ViewModel(){

    private val _allWeathers = MutableStateFlow<List<CurrentWeatherEntity>>(emptyList())
    val allWeathers :StateFlow<List<CurrentWeatherEntity>> = _allWeathers.asStateFlow()

    init {
        getAllWeathersFromDB()
    }


    private fun getAllWeathersFromDB() {
        viewModelScope.launch {
            weatherRepositoryImpl.getAllWeathersFromDB().collect {
                _allWeathers.value = it
            }

        }
    }

    fun searchWeather(cityName: String) {
        viewModelScope.launch {
            weatherRepositoryImpl.fetchCurrentWeatherByCityName(cityName = cityName).collect {}
        }
    }
}