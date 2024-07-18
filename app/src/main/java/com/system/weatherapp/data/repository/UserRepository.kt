package com.system.weatherapp.data.repository


import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.models.User
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUser(user: User){
        userDao.insertUser(user)
    }


    suspend fun getUserbyEmail(email: String): User? {
        return userDao.getUserByEmail(email)
    }

}