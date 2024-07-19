package com.system.weatherapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather")
data class WeatherResponse(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val tempCelsius: String,
    val city: String,
    val country: String,
    val time: String,
    val sunriseTime: String,
    val sunsetTime: String,
    val isDayTime: Boolean
) {

}

