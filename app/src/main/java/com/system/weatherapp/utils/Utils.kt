package com.system.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.util.regex.Pattern

object Utils {

    private val EMAIL_PATTERN =
            Pattern.compile(
                "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            )
        fun isValidEmail(email: String): Boolean {
            return email.isNotEmpty() && EMAIL_PATTERN.matcher(email).matches()
        }

        fun isNetworkAvailable(context : Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)


        }


        fun isValidLength(length: Int): Boolean {
            return length >= 4
        }

    }