package com.system.weatherapp.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.system.weatherapp.R
import com.system.weatherapp.data.models.WeatherResponse
import com.system.weatherapp.data.repository.WeatherRepository
import com.system.weatherapp.utils.Constants
import com.system.weatherapp.utils.mapApiResponseToEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private  val sharedPreferences: SharedPreferences
) : ViewModel() {


    val name = MutableLiveData<String>()
    val cityCountry = MutableLiveData<String>()
    val currentTemperature = MutableLiveData<String>()
    val time = MutableLiveData<String>()
    val sunrise = MutableLiveData<String>()
    val sunset = MutableLiveData<String>()
    val weatherIcon = MutableLiveData<String>()


    private val _weatherHistory = MutableLiveData<List<WeatherResponse>>()
    val weatherHistory: LiveData<List<WeatherResponse>> = _weatherHistory

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {

        fetchWeatherHistory()
    }

    fun fetchWeather(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val weatherdata = weatherRepository.getCurrentWeather(latitude, longitude)
            val weatherResponse = mapApiResponseToEntity((weatherdata))
            weatherRepository.insertWeather(weatherResponse)
            setWeatherUi(weatherResponse)


        }
    }

    private fun setWeatherUi(weatherResponse: WeatherResponse) {
        name.value =Constants.hii +sharedPreferences.getString(Constants.name,"")
        cityCountry.value = "${weatherResponse.city}, ${weatherResponse.country}"
        currentTemperature.value = "${weatherResponse.tempCelsius} °C"
        time.value = weatherResponse.time
        sunrise.value = Constants.sunrise + weatherResponse.sunriseTime
        sunset.value = Constants.sunset  + weatherResponse.sunsetTime
        weatherIcon.value = weatherResponse.weatherIcon
    }

    fun fetchWeatherHistory() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val weatherHistoryData = weatherRepository.getWeatherHistory()
                _weatherHistory.value = weatherHistoryData
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteWeatherTable() {
        viewModelScope.launch {
            _loading.value = true
            try {
                weatherRepository.deleteAllWeather()
                _weatherHistory.value = emptyList()
            } catch (e: Exception) {
                // Handle error
            } finally {
                _loading.value = false
            }
        }
    }


}
