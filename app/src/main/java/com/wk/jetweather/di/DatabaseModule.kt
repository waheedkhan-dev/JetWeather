package com.wk.jetweather.di

import android.content.Context
import androidx.room.Room
import com.wk.jetweather.data.datasource.local.dao.ForecastDao
import com.wk.jetweather.data.datasource.local.database.JetWeatherAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideJetWeatherAppDb(@ApplicationContext context: Context): JetWeatherAppDatabase {
        return Room.databaseBuilder(
            context, JetWeatherAppDatabase::class.java,
            JetWeatherAppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideForecastDao(jetWeatherAppDatabase: JetWeatherAppDatabase): ForecastDao {
        return jetWeatherAppDatabase.forecastDao()
    }

}