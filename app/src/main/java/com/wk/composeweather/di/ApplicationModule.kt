package com.wk.composeweather.di

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
        return "b571f08ae6ca20203f64671baae2cc66"
    }

    @Named("defaultCityName")
    @Singleton
    @Provides
    fun provideDefaultCity(): String {
        return "London"
    }
}