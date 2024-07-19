package com.system.weatherapp.data.db.dao


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.system.weatherapp.data.models.WeatherResponse

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weatherResponse: WeatherResponse)

    @Query("SELECT * FROM weather LIMIT 1")
    suspend fun getWeather(): WeatherResponse?

    @Query("SELECT * FROM weather ORDER BY time DESC")
    suspend fun getWeatherHistory(): List<WeatherResponse>
}
