package com.system.weatherapp.ui.home

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.system.weatherapp.R
import com.system.weatherapp.databinding.FragmentCurrentWeatherBinding
import com.system.weatherapp.ui.viewmodel.WeatherViewModel
import com.system.weatherapp.utils.Utils.isNetworkAvailable
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CurrentWeatherFragment : Fragment() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private var _binding: FragmentCurrentWeatherBinding? = null
    val binding get() = _binding!!

    lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = weatherViewModel

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        checkNetwork()

        weatherViewModel.weatherIcon.observe(viewLifecycleOwner) { url ->
            updateWeatherIcon(url)
        }
        binding.logout.setOnClickListener{
            logout()
        }
        return binding.root
    }


    private fun logout() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(resources.getString(R.string.logout))
        builder.setMessage(resources.getString(R.string.do_you_logout))
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
           clearData()
           dialog.dismiss()
        }
        builder.setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    private fun clearData() {
        weatherViewModel.deleteWeatherTable()
        clearSharedPreferences()
        navigateToLogin()
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.action_currentWeatherFragment_to_loginFragment)
    }

    private fun clearSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

    }

    fun updateWeatherIcon(url: String) {
        Glide.with(this)
            .load(url)
            .placeholder(R.drawable.sun) // Optional placeholder
            .into(binding.imageWeatherIcon)
    }

    fun checkNetwork() {
        if (!isNetworkAvailable(requireContext())) {
            binding.offlineView.visibility = View.VISIBLE
            binding.contentView.visibility = View.GONE
        } else {
            binding.offlineView.visibility = View.GONE
            binding.contentView.visibility = View.VISIBLE
            checkLocationPermission()
        }
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        getCurrentLocation()
    }

    fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                fetchWeather(it.latitude, it.longitude)
            }
        }
    }

    private fun fetchWeather(latitude: Double, longitude: Double) {
        weatherViewModel.fetchWeather(latitude, longitude)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {

                if (!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showSettingsDialog()
                } else {
                    checkLocationPermission()
                }
            }
        }
    }


    private fun showSettingsDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(resources.getString(R.string.permission_required))
            .setMessage(resources.getString(R.string.permission_required_msg))
            .setPositiveButton(resources.getString(R.string.settings)) { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri = Uri.fromParts("package", requireContext().packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
