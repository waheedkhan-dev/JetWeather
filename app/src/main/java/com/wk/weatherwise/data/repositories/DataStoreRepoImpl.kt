package com.wk.weatherwise.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject


class DataStoreRepoImpl @Inject constructor(private val jetWeatherDataStore: DataStore<Preferences>) :
    DataStoreRepository {

    private object PreferencesKey {
        val LAST_ENTERED_CITY_NAME = stringPreferencesKey("last_entered_city_name")
        val LAST_LAT = doublePreferencesKey("last_lat")
        val LAST_LON = doublePreferencesKey("last_lon")
        val IS_INITIAL_LAUNCH = booleanPreferencesKey("is_initial_launch")
    }

    override suspend fun setLastEnteredCityName(cityName: String) {
        jetWeatherDataStore.edit {
            it[PreferencesKey.LAST_ENTERED_CITY_NAME] = cityName
        }
    }

    override suspend fun getLastEnteredCityName(): Flow<String> =
        jetWeatherDataStore.data.map { preferences ->
            preferences[PreferencesKey.LAST_ENTERED_CITY_NAME] ?: ""
        }.catch { exception ->
            if (exception is IOException) {
                emit("")
            } else {
                throw exception
            }
        }

    override suspend fun setIsInitialLaunch(value: Boolean) {
        jetWeatherDataStore.edit {
            it[PreferencesKey.IS_INITIAL_LAUNCH] = value
        }
    }

    override suspend fun isInitialLaunch(): Flow<Boolean>  =
        jetWeatherDataStore.data.map { preferences ->
            preferences[PreferencesKey.IS_INITIAL_LAUNCH] ?: true
        }.catch { exception ->
            if (exception is IOException) {
                emit(true)
            } else {
                throw exception
            }
        }

    override suspend fun setLastLat(lat: Double) {
        jetWeatherDataStore.edit {
            it[PreferencesKey.LAST_LAT] = lat
        }
    }

    override suspend fun getLastLat(): Flow<Double> =
        jetWeatherDataStore.data.map { preferences ->
            preferences[PreferencesKey.LAST_LAT] ?: 0.0
        }.catch { exception ->
            if (exception is IOException) {
                emit(0.0)
            } else {
                throw exception
            }
        }

    override suspend fun setLastLon(lon: Double) {
        jetWeatherDataStore.edit {
            it[PreferencesKey.LAST_LON] = lon
        }
    }

    override suspend fun getLastLon(): Flow<Double> =  jetWeatherDataStore.data.map { preferences ->
        preferences[PreferencesKey.LAST_LON] ?: 0.0
    }.catch { exception ->
        if (exception is IOException) {
            emit(0.0)
        } else {
            throw exception
        }
    }
}