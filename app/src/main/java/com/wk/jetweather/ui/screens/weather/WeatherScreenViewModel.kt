package com.wk.jetweather.ui.screens.weather

import android.content.Context
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.wk.jetweather.data.models.currentWeather.toWeather
import com.wk.jetweather.data.repositories.WeatherRepositoryImpl
import com.wk.jetweather.ui.screens.weather.uistate.HomeScreenUiState
import com.wk.jetweather.utils.Resource
import com.wk.jetweather.utils.location.LocationHelper
import com.wk.jetweather.utils.location.LocationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Named

private const val TAG = "WeatherScreenViewModel"

@HiltViewModel
class CurrentWeatherScreenViewModel @Inject constructor(
    locationHelper: LocationHelper,
    private val weatherRepositoryImpl: WeatherRepositoryImpl,
    private val locationProvider: LocationProvider
) :
    ViewModel() {

    private val _isLocationEnabled = MutableStateFlow(locationHelper.isConnected())
    val isLocationEnabled: StateFlow<Boolean> = _isLocationEnabled.asStateFlow()


    private val _homeScreenUiState =
        MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.InitialState)
    val homeScreenUiState: StateFlow<HomeScreenUiState> = _homeScreenUiState.asStateFlow()


    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchTodayWeather() {
        viewModelScope.launch {
            locationProvider.locationFlow()
                .flatMapLatest { location ->
                    weatherRepositoryImpl.fetchTodayWeatherByLatLon(
                        lat = location.latitude,
                        lon = location.longitude
                    )
                }
                .collect { response ->
                    _homeScreenUiState.update {
                        when (response) {
                            is Resource.Loading -> HomeScreenUiState.Loading
                            is Resource.Success -> HomeScreenUiState.Success(response.data.toWeather())
                            is Resource.Error -> HomeScreenUiState.Error(response.message)
                        }
                    }
                }
        }
    }


    fun enableLocationRequest(
        context: Context,
        makeRequest: (intentSenderRequest: IntentSenderRequest) -> Unit //Lambda to call when locations are off.
    ) {
        val locationRequest = createLocationRequest()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> =
            client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { _ ->
            Timber.tag(TAG).i("enableLocationRequest: LocationService Already Enabled")
        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    val intentSenderRequest =
                        IntentSenderRequest.Builder(exception.resolution)
                            .build()//Create the request prompt
                    makeRequest(intentSenderRequest)//Make the request from UI
                } catch (sendEx: IntentSender.SendIntentException) {
                    Timber.tag(TAG).i(sendEx.toString())
                    // Ignore the error.
                }
            }
        }
    }

    private fun createLocationRequest(): LocationRequest {
        return LocationRequest.Builder(//Create a location request object
            PRIORITY_HIGH_ACCURACY,//Self explanatory
            10000//Interval -> shorter the interval more frequent location updates
        ).build()
    }
}