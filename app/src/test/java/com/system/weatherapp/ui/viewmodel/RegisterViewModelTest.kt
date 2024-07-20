package com.system.weatherapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.system.weatherapp.CoroutineRule
import com.system.weatherapp.R
import com.system.weatherapp.data.models.User
import com.system.weatherapp.data.repository.UserRepository
import com.system.weatherapp.ui.apistates.Result
import com.system.weatherapp.utils.Utils
import com.system.weatherapp.utils.Utils.isValidLength
import com.system.weatherapp.utils.Utils.isValidEmail
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class RegisterViewModelTest {

    private lateinit var registerViewModel: RegisterViewModel
    private lateinit var userRepository: UserRepository
    private val observer: Observer<Result> = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineRule.testDispatcher)
        userRepository = mockk()
        mockkObject(Utils) // Mock static methods in Utils
        registerViewModel = RegisterViewModel(userRepository)
        registerViewModel.result.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after testing
        clearAllMocks()
        unmockkObject(Utils) // Unmock static methods in Utils
    }


    @Test
    fun `register with empty name updates result with please_enter_name`() = coroutineRule.testDispatcher.runBlockingTest {
        registerViewModel.registerUser("", "email@example.com", "password", "password")

        // Verify the observer receives the expected error
        verify { observer.onChanged(Result.ERROR(R.string.pleae_enter_name)) }

        registerViewModel.result.removeObserver(observer)
    }



    @Test
    fun `register with invalid email updates result with invalid_email_format`() = coroutineRule.testDispatcher.runBlockingTest {
        every { isValidEmail(any()) } returns false

        registerViewModel.registerUser("Name", "invalid-email", "password", "password")

        verify { observer.onChanged(Result.ERROR(R.string.invalid_email_format)) }

        registerViewModel.result.removeObserver(observer)
    }

    @Test
    fun `register with non-matching passwords updates result with password_donot_match`() = coroutineRule.testDispatcher.runBlockingTest {
        registerViewModel.registerUser("Name", "email@example.com", "password", "differentPassword")

        // Verify the observer receives the expected error
        verify { observer.onChanged(Result.ERROR(R.string.password_donot_match)) }

        registerViewModel.result.removeObserver(observer)
    }


    @Test
    fun `register with short password updates result with password_length`() = coroutineRule.testDispatcher.runBlockingTest {
        // Mock isValidLength to return false
        every { isValidLength(any()) } returns false

        registerViewModel.registerUser("Name", "email@example.com", "short", "short")

        // Verify the observer receives the expected error
        verify { observer.onChanged(Result.ERROR(R.string.password_length)) }

        registerViewModel.result.removeObserver(observer)
    }


    @Test
    fun `register with existing user updates result with User_exists`() = coroutineRule.testDispatcher.runBlockingTest {
        val email = "existing@example.com"
        val user = User(name = "Existing User", email = email, password = "password")

        coEvery { userRepository.getUserbyEmail(email) } returns user

        registerViewModel.registerUser("Name", email, "password", "password")

        verify { observer.onChanged(Result.ERROR(R.string.User_exists)) }

        registerViewModel.result.removeObserver(observer)
    }

    @Test
    fun `register with new user updates result with registeration_success`() = coroutineRule.testDispatcher.runBlockingTest {
        val email = "new@example.com"

        coEvery { userRepository.getUserbyEmail(email) } returns null
        coEvery { userRepository.insertUser(any()) } just Runs

        registerViewModel.registerUser("Name", email, "password", "password")

        verify { observer.onChanged(Result.SUCCESS(R.string.registeration_success)) }

        registerViewModel.result.removeObserver(observer)
    }

    @Test
    fun `register with exception updates result with registeration_fail`() = coroutineRule.testDispatcher.runBlockingTest {
        val email = "new@example.com"

        coEvery { userRepository.getUserbyEmail(email) } returns null
        coEvery { userRepository.insertUser(any()) } throws Exception()

        registerViewModel.registerUser("Name", email, "password", "password")

        verify { observer.onChanged(Result.ERROR(R.string.registeration_fail)) }

        registerViewModel.result.removeObserver(observer)
    }
}
