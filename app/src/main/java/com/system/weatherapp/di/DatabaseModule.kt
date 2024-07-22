package com.system.weatherapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.system.weatherapp.BuildConfig
import com.system.weatherapp.data.db.AppDatabase
import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.db.dao.WeatherDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent :: class)
@Module
class DatabaseModule {


    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase = SQLiteDatabase.getBytes(BuildConfig.PASS_CODE.toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            "weather_database"
        ).openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    fun provideUserDao(database: AppDatabase) : UserDao {
        return database.userDao()
    }

    @Provides
    fun provideWeatherDao(database: AppDatabase): WeatherDao {
        return database.weatherDao()
    }

    @Provides
    @Singleton
    fun provideSharedPreferInstance(@ApplicationContext appContext: Context) : SharedPreferences {
        val masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            appContext,
            "secure_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }


}