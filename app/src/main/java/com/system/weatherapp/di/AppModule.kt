package com.system.weatherapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.system.weatherapp.data.db.AppDatabase
import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent :: class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) : AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "user_database").build()
    }


    @Provides
    fun provideUserDao(database: AppDatabase) : UserDao {
        return database.userDao()
    }


    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

}