package com.system.weatherapp.ui.splash

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.system.weatherapp.R
import com.system.weatherapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment()
{

    @Inject lateinit var  sharedPreferences : SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.postDelayed({
            navigateToNextScreen()
        }, 2000)
    }

    private fun navigateToNextScreen() {
        val isLogin = sharedPreferences.getBoolean(Constants.isLogin, false)
        Log.d("Validation", isLogin.toString())
        if(isLogin){
            findNavController().navigate(R.id.action_splashFragment_to_currentWeatherFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }

    }
}