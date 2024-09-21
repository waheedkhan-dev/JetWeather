package com.wk.composeweather.data.repositories

import com.wk.composeweather.data.datasource.remote.JetWeatherApi
import com.wk.composeweather.data.models.currentWeather.CurrentWeather
import com.wk.composeweather.utils.Resource
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
    private val jetWeatherApi: JetWeatherApi
) :
    WeatherRepository {
    override suspend fun getCurrentWeather(
        cityName: String
    ): Flow<Resource<CurrentWeather>> {
        return flow {
            try {
                emit(Resource.Loading)
                val response = jetWeatherApi.getCurrentWeather(cityName = cityName, apiKey = apiKey)
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

}