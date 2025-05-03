package com.wk.weatherwise.data.repositories

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {

   suspend fun setLastEnteredCityName(cityName: String)
   suspend fun getLastEnteredCityName(): Flow<String>
   suspend fun setIsInitialLaunch(value : Boolean)
   suspend fun isInitialLaunch() : Flow<Boolean>
   suspend fun setLastLat(lat : Double)
   suspend fun getLastLat() : Flow<Double>
   suspend fun setLastLon(lon : Double)
   suspend fun getLastLon() : Flow<Double>


}