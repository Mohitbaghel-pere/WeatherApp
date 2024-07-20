package com.system.weatherapp.data.repository
import com.system.weatherapp.data.db.dao.UserDao
import com.system.weatherapp.data.models.User
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        userDao = mockk()
        userRepository = UserRepository(userDao)
    }

    @After
    fun tearDown() {
        // Cleanup resources if needed
    }

    @Test
    fun `insertUser should insert user data into the database`() = runTest {
        // Arrange
        val user = User(name = "John Doe", email = "john@example.com", password = "password")
        coEvery { userDao.insertUser(user) } returns Unit

        // Act
        userRepository.insertUser(user)

        // Assert
        coVerify { userDao.insertUser(user) }
    }

    @Test
    fun `getUserbyEmail should return user data from the database`() = runTest {
        // Arrange
        val user = User(name = "John Doe", email = "john@example.com", password = "password")
        val email = "john@example.com"
        coEvery { userDao.getUserByEmail(email) } returns user

        // Act
        val result = userRepository.getUserbyEmail(email)

        // Assert
        assertNotNull(result)
        assertEquals(user, result)
    }
}

