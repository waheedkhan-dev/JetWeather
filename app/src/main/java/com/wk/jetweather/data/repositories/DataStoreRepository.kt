package com.wk.jetweather.data.repositories

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

   suspend fun setLastEnteredCityName(cityName: String)
   suspend fun getLastEnteredCityName(): Flow<String>
   suspend fun setIsInitialLaunch(value : Boolean)
   suspend fun isInitialLaunch() : Flow<Boolean>

}