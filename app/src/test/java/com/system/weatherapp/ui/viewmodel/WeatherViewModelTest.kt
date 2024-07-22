package com.system.weatherapp.ui.viewmodel

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.system.weatherapp.data.models.*
import com.system.weatherapp.data.repository.WeatherRepository
import com.system.weatherapp.utils.Constants
import com.system.weatherapp.utils.mapApiResponseToEntity
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var weatherViewModel: WeatherViewModel
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var sharedPreferences: SharedPreferences
    private val observer: Observer<List<WeatherResponse>> = mockk(relaxed = true)

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)  // Set the main dispatcher to the test dispatcher
        weatherRepository = mockk(relaxed = true)
        sharedPreferences = mockk(relaxed = true)
        weatherViewModel = WeatherViewModel(weatherRepository, sharedPreferences)
        weatherViewModel.weatherHistory.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after testing
        clearAllMocks()
    }

    @Test
    fun `fetchWeather updates live data with weather info`() = runTest(testDispatcher) {
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

        val weatherResponse = mapApiResponseToEntity(apiResponse)
        val latitude = 0.0
        val longitude = 0.0

        coEvery { weatherRepository.getCurrentWeather(latitude, longitude) } returns apiResponse
        coEvery { weatherRepository.insertWeather(weatherResponse) } just Runs
        every { sharedPreferences.getString(Constants.name, "") } returns "Mohit Baghel"

        // Act
        weatherViewModel.fetchWeather(latitude, longitude)

        // Assert
        advanceUntilIdle() // Ensure all coroutines have completed
        Assert.assertEquals("Hii, Mohit Baghel", weatherViewModel.name.value)
        Assert.assertEquals("Test City, US", weatherViewModel.cityCountry.value)
        Assert.assertEquals("${weatherResponse.tempCelsius} °C", weatherViewModel.currentTemperature.value)
        Assert.assertEquals(weatherResponse.time, weatherViewModel.time.value)
        Assert.assertEquals("Sunrise ${weatherResponse.sunriseTime}", weatherViewModel.sunrise.value)
        Assert.assertEquals("Sunset ${weatherResponse.sunsetTime}", weatherViewModel.sunset.value)
        Assert.assertEquals("https://openweathermap.org/img/wn/01d@4x.png", weatherViewModel.weatherIcon.value)
    }

    @Test
    fun `fetchWeatherHistory updates weatherHistory live data`() = runTest(testDispatcher) {
        // Arrange
        val weatherHistoryData = listOf(
            WeatherResponse(
                id = 1,
                tempCelsius = "20.0",
                city = "City1",
                country = "Country1",
                time = "10:00 AM",
                sunriseTime = "5:00 AM",
                sunsetTime = "7:00 PM",
                weatherIcon = "icon_url"
            )
        )

        coEvery { weatherRepository.getWeatherHistory() } returns weatherHistoryData

        // Act
        weatherViewModel.fetchWeatherHistory()

        // Assert
        advanceUntilIdle() // Ensure all coroutines have completed
        verify { observer.onChanged(weatherHistoryData) }
    }

    @Test
    fun `deleteWeatherTable clears weather history`() = runTest(testDispatcher) {
        // Arrange
        coEvery { weatherRepository.deleteAllWeather() } just Runs

        // Act
        weatherViewModel.deleteWeatherTable()

        // Assert
        advanceUntilIdle() // Ensure all coroutines have completed
        Assert.assertTrue(weatherViewModel.weatherHistory.value.isNullOrEmpty())
    }
}
