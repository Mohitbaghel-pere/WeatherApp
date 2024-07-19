package com.system.weatherapp.data.repository


import com.system.weatherapp.data.db.dao.WeatherDao
import com.system.weatherapp.data.models.WeatherApiData
import com.system.weatherapp.data.models.WeatherResponse
import com.system.weatherapp.data.network.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository @Inject constructor(
    private val weatherDao: WeatherDao,
    private val weatherApi : WeatherApi
) {
    suspend fun insertWeather(weatherResponse: WeatherResponse) {
        weatherDao.insertWeather(weatherResponse)
    }

    suspend fun getWeatherHistory(): List<WeatherResponse> {
        return weatherDao.getWeatherHistory()
    }

    suspend fun getCurrentWeather(lat : Double, lng : Double) : WeatherApiData{
        return weatherApi.getWeather(lat, lng)
    }



}
