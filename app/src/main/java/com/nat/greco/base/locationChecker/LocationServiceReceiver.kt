package com.nat.greco.base.locationChecker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager

class LocationServiceReceiver(
    val context: Context,
    val isLocationEnabled: (Boolean) -> Unit
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (LocationManager.PROVIDERS_CHANGED_ACTION == intent.action) {
            isLocationEnabled(LocationHandler.isLocationServiceEnabled(context))
        }
    }
}