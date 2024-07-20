package com.system.weatherapp.data.models


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class WeatherResponseTest {

    @Test
    fun `test WeatherResponse creation`() {
        // Arrange
        val id = 1L
        val tempCelsius = "25.0"
        val city = "Test City"
        val country = "Test Country"
        val time = "12:00 PM"
        val sunriseTime = "6:00 AM"
        val sunsetTime = "6:00 PM"
        val weatherIcon = "icon_url"

        // Act
        val weatherResponse = WeatherResponse(
            id = id,
            tempCelsius = tempCelsius,
            city = city,
            country = country,
            time = time,
            sunriseTime = sunriseTime,
            sunsetTime = sunsetTime,
            weatherIcon = weatherIcon
        )

        // Assert
        assertEquals(id, weatherResponse.id)
        assertEquals(tempCelsius, weatherResponse.tempCelsius)
        assertEquals(city, weatherResponse.city)
        assertEquals(country, weatherResponse.country)
        assertEquals(time, weatherResponse.time)
        assertEquals(sunriseTime, weatherResponse.sunriseTime)
        assertEquals(sunsetTime, weatherResponse.sunsetTime)
        assertEquals(weatherIcon, weatherResponse.weatherIcon)
    }

    @Test
    fun `test WeatherResponse equality`() {
        // Arrange
        val weatherResponse1 = WeatherResponse(
            id = 1L,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "icon_url"
        )

        val weatherResponse2 = WeatherResponse(
            id = 1L,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "icon_url"
        )

        // Act & Assert
        assertEquals(weatherResponse1, weatherResponse2)
    }

    @Test
    fun `test WeatherResponse inequality`() {
        // Arrange
        val weatherResponse1 = WeatherResponse(
            id = 1L,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "icon_url"
        )

        val weatherResponse2 = WeatherResponse(
            id = 2L,
            tempCelsius = "20.0",
            city = "Different City",
            country = "Different Country",
            time = "1:00 PM",
            sunriseTime = "5:00 AM",
            sunsetTime = "7:00 PM",
            weatherIcon = "different_icon_url"
        )

        // Act & Assert
        assertNotEquals(weatherResponse1, weatherResponse2)
    }

    @Test
    fun `test WeatherResponse default values`() {
        // Act
        val weatherResponse = WeatherResponse(
            id = 0L,
            tempCelsius = "",
            city = "",
            country = "",
            time = "",
            sunriseTime = "",
            sunsetTime = "",
            weatherIcon = ""
        )

        // Assert
        assertEquals(0L, weatherResponse.id)
        assertEquals("", weatherResponse.tempCelsius)
        assertEquals("", weatherResponse.city)
        assertEquals("", weatherResponse.country)
        assertEquals("", weatherResponse.time)
        assertEquals("", weatherResponse.sunriseTime)
        assertEquals("", weatherResponse.sunsetTime)
        assertEquals("", weatherResponse.weatherIcon)
    }
}
