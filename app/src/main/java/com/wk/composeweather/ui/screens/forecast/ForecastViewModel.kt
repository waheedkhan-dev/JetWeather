package com.wk.composeweather.ui.screens.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wk.composeweather.data.repositories.WeatherRepositoryImpl
import com.wk.composeweather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ForecastViewModel @Inject constructor(
    @Named("defaultCityName") private val defaultCity: String,
    private val weatherRepositoryImpl: WeatherRepositoryImpl
) : ViewModel() {

    init {
       // fetchFiveDayForecast()
    }

    private fun fetchFiveDayForecast() {
        viewModelScope.launch {
            weatherRepositoryImpl.fetchFiveDayForecast(cityName = defaultCity).collect { response->
                when(response){
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {

                    }
                    is Resource.Error -> {

                    }
                }

            }
        }
    }
}