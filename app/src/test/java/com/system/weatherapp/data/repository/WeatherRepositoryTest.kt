package com.system.weatherapp.data.repository

import com.system.weatherapp.data.db.dao.WeatherDao
import com.system.weatherapp.data.models.*
import com.system.weatherapp.data.network.WeatherApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {

    private lateinit var weatherDao: WeatherDao
    private lateinit var weatherApi: WeatherApi
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setUp() {
        weatherDao = mockk(relaxed = true)
        weatherApi = mockk(relaxed = true)
        weatherRepository = WeatherRepository(weatherDao, weatherApi)
    }

    @After
    fun tearDown() {
        // Cleanup resources if needed
    }

    @Test
    fun `insertWeather should insert weather data into the database`() = runBlockingTest {
        // Arrange
        val weatherResponse = WeatherResponse(
            id = 1,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "true"
        )

        // Act
        weatherRepository.insertWeather(weatherResponse)

        // Assert
        coVerify { weatherDao.insertWeather(weatherResponse) }
    }

    @Test
    fun `getWeatherHistory should return list of weather data`() = runBlockingTest {
        // Arrange
        val weatherResponse = WeatherResponse(
            id = 1,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "true"
        )
        val weatherList = listOf(weatherResponse)
        coEvery { weatherDao.getWeatherHistory() } returns weatherList

        // Act
        val result = weatherRepository.getWeatherHistory()

        // Assert
        assertNotNull(result)
        assertEquals(weatherList, result)
    }

    @Test
    fun `getCurrentWeather should return weather data from API`() = runBlockingTest {
        // Arrange
        val apiResponse = WeatherApiData(
            coord = Coord(0.0, 0.0),
            weather = listOf(Weather(1, "Clear", "clear sky", "01d")),
            base = "stations",
            main = Main(298.15, 0.0, 0.0, 0.0, 0, 0, 0, 0),
            visibility = 10000,
            wind = Wind(0.0, 0, 0.0),
            rain = null,
            clouds = Clouds(0),
            dt = System.currentTimeMillis() / 1000,
            sys = Sys(0, 0, "US", System.currentTimeMillis() / 1000 - 3600, System.currentTimeMillis() / 1000 + 3600),
            timezone = 0,
            id = 0,
            name = "Test City",
            cod = 200
        )
        val latitude = 0.0
        val longitude = 0.0
        coEvery { weatherApi.getWeather(latitude, longitude) } returns apiResponse

        // Act
        val result = weatherRepository.getCurrentWeather(latitude, longitude)

        // Assert
        assertNotNull(result)
        assertEquals(apiResponse, result)
    }

    @Test
    fun `deleteAllWeather should clear all weather data from the database`() = runBlockingTest {
        // Act
        weatherRepository.deleteAllWeather()

        // Assert
        coVerify { weatherDao.deleteAllWeather() }
    }
}
