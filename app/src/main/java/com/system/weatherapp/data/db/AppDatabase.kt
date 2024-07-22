package com.system.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.db.dao.WeatherDao
import com.system.weatherapp.data.models.User
import com.system.weatherapp.data.models.WeatherResponse

@Database(entities = [WeatherResponse::class, User:: class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
    abstract fun userDao(): UserDao

}

