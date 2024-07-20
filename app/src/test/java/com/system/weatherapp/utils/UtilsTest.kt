package com.system.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class UtilsTest {

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var connectivityManager: ConnectivityManager

    @Mock
    private lateinit var networkCapabilities: NetworkCapabilities

    @Mock
    private lateinit var network: Network

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
    }

    @Test
    fun `isValidEmail returns true for valid email`() {
        assertTrue(Utils.isValidEmail("test@example.com"))
    }

    @Test
    fun `isValidEmail returns false for invalid email`() {
        assertFalse(Utils.isValidEmail("invalid-email"))
    }

    @Test
    fun `isValidLength returns true for length greater than or equal to 4`() {
        assertTrue(Utils.isValidLength(4))
        assertTrue(Utils.isValidLength(10))
    }

    @Test
    fun `isValidLength returns false for length less than 4`() {
        assertFalse(Utils.isValidLength(3))
    }

    @Test
    fun `isNetworkAvailable returns true when network is available and has internet capability`() {
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(true)

        assertTrue(Utils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable returns false when network is not available`() {
        `when`(connectivityManager.activeNetwork).thenReturn(null)

        assertFalse(Utils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable returns false when network capabilities are null`() {
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(null)

        assertFalse(Utils.isNetworkAvailable(context))
    }

    @Test
    fun `isNetworkAvailable returns false when network does not have internet capability`() {
        `when`(connectivityManager.activeNetwork).thenReturn(network)
        `when`(connectivityManager.getNetworkCapabilities(network)).thenReturn(networkCapabilities)
        `when`(networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)).thenReturn(false)

        assertFalse(Utils.isNetworkAvailable(context))
    }
}
