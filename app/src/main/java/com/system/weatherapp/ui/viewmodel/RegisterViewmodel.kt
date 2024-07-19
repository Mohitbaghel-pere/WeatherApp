package com.system.weatherapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.system.weatherapp.R
import com.system.weatherapp.data.models.User
import com.system.weatherapp.data.repository.UserRepository
import com.system.weatherapp.ui.apistates.Result
import com.system.weatherapp.utils.Utils.Companion.isValidLength
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

    private val userRepository: UserRepository
) : ViewModel() {

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result> = _result

    fun registerUser(name: String, email: String, password: String, confirmPassword: String) {

        if (name.isEmpty()) {
            _result.value = Result.ERROR(R.string.pleae_enter_name)
            return
        }
        if (!isValidEmail(email)) {
            _result.value =Result.ERROR(R.string.invalid_email_format)
            return
        }

        if (password != confirmPassword) {
            _result.value = Result.ERROR(R.string.password_donot_match)
            return
        }

        if (!isValidLength(password.length)) {
            _result.value = Result.ERROR(R.string.password_length)
            return
        }

        viewModelScope.launch {
            try {

                val existingUser = userRepository.getUserbyEmail(email)
                if (existingUser != null) {
                    _result.value = Result.ERROR(R.string.User_exists)
                    return@launch
                }
                val user = User(name = name, email = email, password = password)
                userRepository.insertUser(user)
                _result.value = Result.SUCCESS(R.string.registeration_success)
            } catch (e: Exception) {
                _result.value = Result.ERROR(R.string.registeration_fail)
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
