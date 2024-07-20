package com.system.weatherapp.data.models

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class UserTest {

    @Test
    fun `test User creation`() {
        // Arrange
        val id = 1
        val name = "Mohit"
        val email = "mohit@test.com"
        val password = "password"

        // Act
        val user = User(
            id = id,
            name = name,
            email = email,
            password = password
        )

        // Assert
        assertEquals(id, user.id)
        assertEquals(name, user.name)
        assertEquals(email, user.email)
        assertEquals(password, user.password)
    }

    @Test
    fun `test User equality`() {
        // Arrange
        val user1 = User(
            id = 1,
            name = "Mohit",
            email = "mohit@test.com",
            password = "password"
        )

        val user2 = User(
            id = 1,
            name = "Mohit",
            email = "mohit@test.com",
            password = "password"
        )

        // Act & Assert
        assertEquals(user1, user2)
    }

    @Test
    fun `test User inequality`() {
        // Arrange
        val user1 = User(
            id = 1,
            name = "Mohit",
            email = "mohit@test.com",
            password = "password"
        )

        val user2 = User(
            id = 2,
            name = "Another Name",
            email = "another@test.com",
            password = "anotherpassword"
        )

        // Act & Assert
        assertNotEquals(user1, user2)
    }

    @Test
    fun `test User default values`() {
        // Act
        val user = User(
            id = 0,
            name = "",
            email = "",
            password = ""
        )

        // Assert
        assertEquals(0, user.id)
        assertEquals("", user.name)
        assertEquals("", user.email)
        assertEquals("", user.password)
    }
}
