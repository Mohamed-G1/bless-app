package com.nat.bless.screens.splash.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nat.bless.R
import com.nat.bless.base.locationChecker.LocationPermissionsChecker
import com.nat.bless.base.permissions.PermissionViewModel

@Composable
fun SplashScreen(
    state: SplashState,
    event: ((SplashEvent) -> Unit)? = null,
    navigateToNext: (() -> Unit)? = null,
) {
    val activity = LocalContext.current as Activity

    var isLocationGranted by remember {
        mutableStateOf(LocationPermissionsChecker.isLocationPermissionGranted(activity))
    }

    var launchTheNavigator by remember { mutableStateOf(false) }


    if (state.shouldNavigate) {
        LaunchedEffect(Unit) {
            navigateToNext?.invoke()
        }
    }


    val permissionsToRequest = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.POST_NOTIFICATIONS
    )


    val viewModel = viewModel<PermissionViewModel>()
    val dialogQueue = viewModel.visiblePermissionDialogQueue
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                val isGranted = perms[permission] == true
                viewModel.onPermissionResult(
                    permission = permission,
                    isGranted = isGranted
                )

                isLocationGranted =
                    perms[android.Manifest.permission.ACCESS_FINE_LOCATION] == true || perms[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true

                if (isLocationGranted) {
                    launchTheNavigator = true
                }
            }
        }
    )



    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Image with space at the bottom
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            modifier = Modifier.size(150.dp),

            )

    }


}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

@Preview
@Preview(locale = "ar")
@Composable
private fun SplashViewPreview() {
    SplashScreen(state = SplashState())
}