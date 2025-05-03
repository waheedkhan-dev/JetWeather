package com.wk.weatherwise.di

import com.wk.weatherwise.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Named("ApiKey")
    @Singleton
    @Provides
    fun provideApiKey(): String {
        return BuildConfig.APP_KEY
    }

    @Named("defaultCityName")
    @Singleton
    @Provides
    fun provideDefaultCity(): String {
        return "Attock" // This is a static city name for current weather. In production level application we have to fetch the user current location and fetch the weather for that location
    }
}