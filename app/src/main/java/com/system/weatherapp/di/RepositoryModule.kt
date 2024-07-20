package com.system.weatherapp.di

import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.db.dao.WeatherDao
import com.system.weatherapp.data.network.WeatherApi
import com.system.weatherapp.data.repository.UserRepository
import com.system.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent :: class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDao: WeatherDao, weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepository(weatherDao, weatherApi)
    }
}