package com.system.weatherapp.ui.apistates

sealed class Result {
    object LOADING : Result()
    data class SUCCESS(val message: Int) : Result()
    data class ERROR(val message: Int) : Result()
}