package com.wk.jetweather.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
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
}