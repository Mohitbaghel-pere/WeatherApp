package com.system.weatherapp.utils


import com.system.weatherapp.data.models.WeatherApiData
import com.system.weatherapp.data.models.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*

fun mapApiResponseToEntity(apiResponse: WeatherApiData): WeatherResponse {
    val tempCelsius = "%.2f".format(apiResponse.main.temp - 273.15)
    val time = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(apiResponse.dt * 1000))
    val sunriseTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(apiResponse.sys.sunrise * 1000))
    val sunsetTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(apiResponse.sys.sunset * 1000))

    val currentTime = System.currentTimeMillis()
    val isDayTime = currentTime in (apiResponse.sys.sunrise * 1000)..(apiResponse.sys.sunset * 1000)
    val weatherIconCode = apiResponse.weather.firstOrNull()?.icon ?: "01d"
    val weatherIconUrl = "https://openweathermap.org/img/wn/$weatherIconCode@4x.png"


    return WeatherResponse(
        id = 0,
        tempCelsius = tempCelsius,
        city = apiResponse.name,
        country = apiResponse.sys.country,
        time = time,
        sunriseTime = sunriseTime,
        sunsetTime = sunsetTime,
        weatherIcon = weatherIconUrl
    )
}
