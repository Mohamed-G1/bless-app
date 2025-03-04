package com.nat.couriersapp.base.locationChecker

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.IntentFilter
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.nat.couriersapp.base.permissions.LocationPermissionDialog
import com.nat.couriersapp.base.permissions.LocationPermissionTextProvider
import com.nat.couriersapp.base.permissions.PermissionDialog
import com.nat.couriersapp.base.permissions.PermissionViewModel
import com.nat.couriersapp.screens.splash.presentation.openAppSettings
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@SuppressLint("NewApi")
object LocationHandler {
    @SuppressLint("MissingPermission")
    suspend fun getCurrentLocation(context: Context): Location? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        return suspendCancellableCoroutine { continuation ->
            val locationRequest = LocationRequest.Builder(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                1000
            ).build()
            fusedLocationClient.getCurrentLocation(
                com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY,
                null
            ).addOnSuccessListener { location ->
                continuation.resume(location)
            }.addOnFailureListener { exception ->
                continuation.resume(null)
            }
        }
    }

    fun isLocationServiceEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}


