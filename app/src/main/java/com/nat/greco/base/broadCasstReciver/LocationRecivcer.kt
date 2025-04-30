package com.nat.greco.base.broadCasstReciver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager

object LocationReceiver : BroadcastReceiver() {
   private var locationStateUpdate: ((Boolean) -> Unit)? = null

    // Function to set the callback
    fun setLocationStateUpdateCallback(callback: (Boolean) -> Unit) {
        locationStateUpdate = callback
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {
            // Check if the location provider is enabled or not
            val locationManager =
                context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            // If either GPS or Network provider is enabled, location is enabled
            locationStateUpdate?.invoke(isGpsEnabled || isNetworkEnabled)
        }
    }
}