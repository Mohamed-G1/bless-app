package com.nat.bless.screens.addNewClient.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.ui.theme.CompactTypography


@Composable
fun MapScreen(
    onLocationPicked: (name: String, lat: String, lng: String) -> Unit,
    onClose: () -> Unit
) {
    val context = LocalContext.current
    var locationName by remember { mutableStateOf("") }
    var lat by remember { mutableDoubleStateOf(0.0) }
    var lng by remember { mutableDoubleStateOf(0.0) }
    var permissionRequested by remember { mutableStateOf(false) }

    val fetchLocation: () -> Unit = {
        try {
            val fused = com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
            fused.lastLocation.addOnSuccessListener { loc ->
                if (loc != null) {
                    lat = loc.latitude
                    lng = loc.longitude
                    try {
                        val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
                        val addresses = geocoder.getFromLocation(lat, lng, 1)
                        if (!addresses.isNullOrEmpty()) {
                            locationName = addresses[0].getAddressLine(0) ?: ""
                        }
                    } catch (_: Exception) { /* ignore geocoder errors */ }
                }
            }
        } catch (_: Exception) { /* fused location may throw if Google Play services missing */ }
    }

    val permissionLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestPermission()
    ) { granted: Boolean ->
        if (granted) {
            fetchLocation()
        }
    }

    androidx.compose.runtime.LaunchedEffect(Unit) {
        if (!permissionRequested) {
            permissionRequested = true
            val hasPermission = androidx.core.content.ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED

            if (hasPermission) {
                fetchLocation()
            } else {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x88000000)),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "اختر الموقع",
                    style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = if (locationName.isNotBlank()) locationName else "Lat: $lat  Lng: $lng",
                    style = CompactTypography.labelMedium.copy(fontSize = 14.sp)
                )
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    AppButton(
                        text = "رجوع",
                        onClick = { onClose() }
                    )
                    Spacer(Modifier.width(8.dp))
                    AppButton(
                        text = "اختر",
                        onClick = { onLocationPicked(locationName, lat.toString(), lng.toString()) }
                    )
                }
            }
        }
    }
}