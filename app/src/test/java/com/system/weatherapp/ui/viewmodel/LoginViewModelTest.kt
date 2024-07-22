package com.system.weatherapp.ui.viewmodel

import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.system.weatherapp.CoroutineRule
import com.system.weatherapp.R
import com.system.weatherapp.data.models.User
import com.system.weatherapp.data.repository.UserRepository
import com.system.weatherapp.ui.viewmodel.LoginViewModel.LoginState
import com.system.weatherapp.utils.Constants
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var userRepository: UserRepository
    private lateinit var sharedPreferences: SharedPreferences
    private val observer: Observer<LoginState> = mockk(relaxed = true)

    @get:Rule
    val coroutineRule = CoroutineRule()

    @get:Rule
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineRule.testDispatcher)
        sharedPreferences = mockk(relaxed = true)
        userRepository = mockk()
        loginViewModel = LoginViewModel(userRepository, sharedPreferences)
        loginViewModel.loginState.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after testing
        clearAllMocks()
    }

    @Test
    fun `login with invalid email updates loginState with InvalidEmail`() = coroutineRule.testDispatcher.runBlockingTest {
        loginViewModel.login("invalid-email", "password")

        verify { observer.onChanged(LoginState.InvalidEmail) }

        loginViewModel.loginState.removeObserver(observer)
    }

    @Test
    fun `login with empty password updates loginState with InvalidPassword`() = coroutineRule.testDispatcher.runBlockingTest {
        loginViewModel.login("email@example.com", "")

        verify { observer.onChanged(LoginState.InvalidPassword) }

        loginViewModel.loginState.removeObserver(observer)
    }

    @Test
    fun `login with valid credentials updates loginState with Success`() = coroutineRule.testDispatcher.runBlockingTest {
        val email = "valid@example.com"
        val password = "password"
        val user = User(name = "Mohit Baghel", email = email, password = password)

        coEvery { userRepository.getUserbyEmail(email) } returns user

        loginViewModel.login(email, password)

        verify { observer.onChanged(LoginState.Loading) }
        verify { observer.onChanged(LoginState.Success) }

        verify { sharedPreferences.edit().putString(Constants.name, user.name).apply() }

        loginViewModel.loginState.removeObserver(observer)
    }

    @Test
    fun `login with invalid credentials updates loginState with Error`() = coroutineRule.testDispatcher.runBlockingTest {
        val email = "invalid@example.com"
        val password = "wrongpassword"

        coEvery { userRepository.getUserbyEmail(email) } returns null

        loginViewModel.login(email, password)

        verify { observer.onChanged(LoginState.Loading) }
        verify { observer.onChanged(LoginState.Error(R.string.invalid_email_pass)) }

        loginViewModel.loginState.removeObserver(observer)
    }

    @Test
    fun `login with valid email and wrong password updates loginState with Error`() = coroutineRule.testDispatcher.runBlockingTest {
        val email = "valid@example.com"
        val password = "wrongpassword"
        val user = User(name = "Mohit Baghel", email = email, password = "correctpassword")

        coEvery { userRepository.getUserbyEmail(email) } returns user

        loginViewModel.login(email, password)

        verify { observer.onChanged(LoginState.Loading) }
        verify { observer.onChanged(LoginState.Error(R.string.invalid_email_pass)) }

        loginViewModel.loginState.removeObserver(observer)
    }
}
