package com.system.weatherapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.system.weatherapp.BuildConfig
import com.system.weatherapp.data.db.AppDatabase
import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.db.dao.WeatherDao
import com.system.weatherapp.data.network.RetrofitInstance
import com.system.weatherapp.data.network.WeatherApi
import com.system.weatherapp.data.repository.UserRepository
import com.system.weatherapp.data.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@InstallIn(SingletonComponent :: class)
@Module
class AppModule {



    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(
            context, AppDatabase::class.java,
            "user_database"
        ).build()
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
    fun provideWeatherApi(): WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(weatherDao: WeatherDao, weatherApi: WeatherApi): WeatherRepository {
        return WeatherRepository(weatherDao, weatherApi)
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