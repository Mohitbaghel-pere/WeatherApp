package com.system.weatherapp.data.network

import com.system.weatherapp.BuildConfig
import com.system.weatherapp.data.models.WeatherApiData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    suspend fun getWeather(
        @Query("lat") lat : Double,
        @Query("lon") lng : Double,
        @Query("appid")  apiKey : String = BuildConfig.OPEN_WEATHER_API_KEY
    ) : WeatherApiData

}