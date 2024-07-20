package com.system.weatherapp.utils

import com.system.weatherapp.data.models.Clouds
import com.system.weatherapp.data.models.Coord
import com.system.weatherapp.data.models.Main
import com.system.weatherapp.data.models.Sys
import com.system.weatherapp.data.models.Weather
import com.system.weatherapp.data.models.WeatherApiData
import com.system.weatherapp.data.models.Wind
import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MapApiResponseToEntityKtTest {

    @Test
    fun `mapApiResponseToEntity_correctly_maps_WeatherApiData_to_WeatherResponse`() {
        val coord = Coord(0.0, 0.0)
        val weather = listOf(Weather(800, "Clear", "clear sky", "01d"))
        val main = Main(300.15, 298.72, 80.2, 300.15, 1015, 0,64, 933)
        val wind = Wind(1.0, 1, 1.0)
        val clouds = Clouds(0)
        val sys = Sys(1, 1, "US", 1628890800, 1661882248)
        val weatherApiData = WeatherApiData(
            coord = coord,
            weather = weather,
            base = "stations",
            main = main,
            visibility = 10000,
            wind = wind,
            rain = null,
            clouds = clouds,
            dt = 1628853600,
            sys = sys,
            timezone = -25200,
            id = 0,
            name = "Test City",
            cod = 200
        )

        val expectedTemperature = "27.00"  // Since we set tempCelsius directly for test case
        val expectedTime = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault()).format(Date(weatherApiData.dt * 1000))
        val expectedSunriseTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(weatherApiData.sys.sunrise * 1000))
        val expectedSunsetTime = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(weatherApiData.sys.sunset * 1000))
        val expectedWeatherIcon = "https://openweathermap.org/img/wn/01d@4x.png"

        val weatherResponse = mapApiResponseToEntity(weatherApiData)

        assertEquals(expectedTemperature, weatherResponse.tempCelsius)
        assertEquals("Test City", weatherResponse.city)
        assertEquals("US", weatherResponse.country)
        assertEquals(expectedTime, weatherResponse.time)
        assertEquals(expectedSunriseTime, weatherResponse.sunriseTime)
        assertEquals(expectedSunsetTime, weatherResponse.sunsetTime)
        assertEquals(expectedWeatherIcon, weatherResponse.weatherIcon)
    }
}