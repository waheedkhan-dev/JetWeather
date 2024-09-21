package com.wk.jetweather.data.repositories

import com.wk.jetweather.data.datasource.local.dao.ForecastDao
import com.wk.jetweather.data.datasource.local.entities.Forecast
import com.wk.jetweather.data.datasource.remote.JetWeatherApi
import com.wk.jetweather.data.models.currentWeather.CurrentWeather
import com.wk.jetweather.data.models.fiveDayForecast.FiveDayForecast
import com.wk.jetweather.data.models.fiveDayForecast.toForecastList
import com.wk.jetweather.utils.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named

class WeatherRepositoryImpl @Inject constructor(
    @Named("ApiKey") private val apiKey: String,
    private val jetWeatherApi: JetWeatherApi,
    private val forecastDao: ForecastDao
) :
    WeatherRepository {
    override suspend fun fetchTodayWeather(
        cityName: String
    ): Flow<Resource<CurrentWeather>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = jetWeatherApi.fetchTodayWeather(cityName = cityName, apiKey = apiKey)
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val code = jObjError["cod"]
                        val message = jObjError["message"]
                        emit(Resource.Error("$code: $message"))
                    } catch (ex: Exception) {
                        emit(Resource.Error(ex.let { it.message.toString() }))
                    }
                }
            } catch (ex: IOException) {
                emit(Resource.Error("No internet connection"))
            } catch (ex: HttpException) {
                emit(Resource.Error("Something went wrong!"))
            }

        }.flowOn(IO)
    }

    override suspend fun fetchFiveDayForecast(
        cityName: String
    ): Flow<Resource<List<Forecast>>> {
        return flow {
            try {
                // Check the database for cached forecast data
                val cachedForecast = forecastDao.getForecastByCity(city = cityName)

                // Emit cached data first if available
                if (cachedForecast.isNotEmpty()) {
                    emit(Resource.Success(cachedForecast))
                    return@flow
                } else {
                    // Emit loading state if no data is available in the database
                    emit(Resource.Loading)
                }

                val response = jetWeatherApi.fetchFiveDayForecast(cityName = cityName, apiKey = apiKey)
                if (response.isSuccessful) {
                    response.body()?.let {
                        val forecastList =  it.toForecastList()
                        forecastDao.insertForecasts(forecastList)
                    }
                    emit(Resource.Success(forecastDao.getForecastByCity(city = cityName)))
                } else {
                    try {
                        val jObjError = JSONObject(response.errorBody()!!.string())
                        val code = jObjError["cod"]
                        val message = jObjError["message"]
                        emit(Resource.Error("$code: $message"))
                    } catch (ex: Exception) {
                        emit(Resource.Error(ex.let { it.message.toString() }))
                    }
                }
            } catch (ex: IOException) {
                emit(Resource.Error("No internet connection"))
            } catch (ex: HttpException) {
                emit(Resource.Error("Something went wrong!"))
            }

        }.flowOn(IO)
    }

}