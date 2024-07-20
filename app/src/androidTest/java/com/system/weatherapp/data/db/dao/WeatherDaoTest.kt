package com.system.weatherapp.data.db.dao


import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.system.weatherapp.data.db.AppDatabase
import com.system.weatherapp.data.models.WeatherResponse
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WeatherDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var weatherDao: WeatherDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        weatherDao = database.weatherDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveWeather() = runBlocking {
        val weatherResponse = WeatherResponse(
            id = 1,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "sun"
        )
        weatherDao.insertWeather(weatherResponse)

        val retrievedWeather = weatherDao.getWeather()
        assertNotNull(retrievedWeather)
        assertEquals(weatherResponse.tempCelsius, retrievedWeather?.tempCelsius)
        assertEquals(weatherResponse.city, retrievedWeather?.city)
        assertEquals(weatherResponse.country, retrievedWeather?.country)
        assertEquals(weatherResponse.time, retrievedWeather?.time)
        assertEquals(weatherResponse.sunriseTime, retrievedWeather?.sunriseTime)
        assertEquals(weatherResponse.sunsetTime, retrievedWeather?.sunsetTime)
        assertEquals(weatherResponse.weatherIcon, retrievedWeather?.weatherIcon)
    }

    @Test
    fun getWeatherHistory() = runBlocking {
        val weatherResponse1 = WeatherResponse(
            id = 1,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "sun"
        )
        val weatherResponse2 = WeatherResponse(
            id = 2,
            tempCelsius = "26.0",
            city = "Another City",
            country = "Another Country",
            time = "1:00 PM",
            sunriseTime = "6:30 AM",
            sunsetTime = "6:30 PM",
            weatherIcon = "cloud"
        )
        weatherDao.insertWeather(weatherResponse1)
        weatherDao.insertWeather(weatherResponse2)

        val weatherHistory = weatherDao.getWeatherHistory()
        assertEquals(2, weatherHistory.size)
        assertEquals(weatherResponse2.tempCelsius, weatherHistory[0].tempCelsius)
        assertEquals(weatherResponse1.tempCelsius, weatherHistory[1].tempCelsius)
    }

    @Test
    fun deleteAllWeather() = runBlocking {
        val weatherResponse = WeatherResponse(
            id = 1,
            tempCelsius = "25.0",
            city = "Test City",
            country = "Test Country",
            time = "12:00 PM",
            sunriseTime = "6:00 AM",
            sunsetTime = "6:00 PM",
            weatherIcon = "sun"
        )
        weatherDao.insertWeather(weatherResponse)

        weatherDao.deleteAllWeather()
        val weatherHistory = weatherDao.getWeatherHistory()
        assertEquals(0, weatherHistory.size)
    }
}
