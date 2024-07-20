package com.system.weatherapp.data.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.system.weatherapp.data.db.AppDatabase
import com.system.weatherapp.data.models.User
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var userDao: UserDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        userDao = database.userDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveUser() = runBlocking {
        val user = User(id = 0, name = "Mohit", email = "mohit@test.com", password = "password")
        userDao.insertUser(user)

        val retrievedUser = userDao.getUserByEmail("mohit@test.com")
        assertNotNull(retrievedUser)
        assertEquals(user.name, retrievedUser?.name)
        assertEquals(user.email, retrievedUser?.email)
        assertEquals(user.password, retrievedUser?.password)
    }


    @Test
    fun insertUserWithSameEmailReplacesOldUser() = runBlocking {
        val user1 = User(id = 0, name = "Mohit", email = "mohit@test.com", password = "password1")
        userDao.insertUser(user1)

        val user2 = User(id = 1, name = "Mohit Updated", email = "mohit@test.com", password = "password2")
        userDao.insertUser(user2)

        val retrievedUser = userDao.getUserByEmail("mohit@test.com")
        assertNotNull(retrievedUser)
        assertEquals(user2.name, retrievedUser?.name)
        assertEquals(user2.email, retrievedUser?.email)
        assertEquals(user2.password, retrievedUser?.password)
    }
}
