package com.system.weatherapp.ui.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.weatherapp.R
import com.system.weatherapp.data.repository.UserRepository
import com.system.weatherapp.utils.Constants
import com.system.weatherapp.utils.Utils.isValidEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences

) : ViewModel() {

    sealed class LoginState {
        data object Loading : LoginState()
        data object Success : LoginState()
        data class Error(val message: Int) : LoginState()
        data object InvalidEmail : LoginState()
        data object InvalidPassword : LoginState()
    }

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> get() = _loginState

    fun login(email: String, password: String) {
        if (!isValidEmail(email)) {
            _loginState.value = LoginState.InvalidEmail
            return
        }

        if (password.isEmpty()) {
            _loginState.value = LoginState.InvalidPassword
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            val user = userRepository.getUserbyEmail(email)
            if (user != null && user.password == password) {
                sharedPreferences.edit().putString(Constants.name, user!!.name ).apply()
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error(R.string.invalid_email_pass)
            }
        }
    }
}
