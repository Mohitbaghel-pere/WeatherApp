package com.system.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Patterns
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.system.weatherapp.R

class Utils {

    companion object {

        fun isValidEmail(email: String): Boolean {
            return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isNetworkAvailable(context : Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)


        }

        // add password validations length

        fun isValidLength(length: Int): Boolean {
            return length >= 4
        }



    }
}


@BindingAdapter("srcCompat")
fun setImageResource(view: ImageView, resourceId: LiveData<Int>?) {
    resourceId?.let {
        view.setImageResource(it.value ?: R.drawable.sun) // Use a default image if resourceId is null
    }
}

