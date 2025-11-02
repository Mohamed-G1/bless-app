package com.nat.greco.screens.splash.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nat.greco.R
import com.nat.greco.base.locationChecker.LocationPermissionsChecker
import com.nat.greco.base.permissions.LocationPermissionTextProvider
import com.nat.greco.base.permissions.PermissionDialog
import com.nat.greco.base.permissions.PermissionViewModel
import com.nat.greco.ui.theme.CompactTypography
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
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

//    if (launchTheNavigator) {
//        LaunchedEffect(Unit) {
//            event?.invoke(SplashEvent.Navigate)
////            navigateToNext?.invoke()
//        }
//        launchTheNavigator = false
//    }


    // This to recheck location permission after returning from settings
//    val lifecycleOwner = LocalLifecycleOwner.current
//    DisposableEffect(lifecycleOwner) {
//        val lifecycleObserver = LifecycleEventObserver { _, event ->
//            if (event == Lifecycle.Event.ON_RESUME) {
//                isLocationGranted = LocationPermissionsChecker.isLocationPermissionGranted(activity)
//            }
//
//        }
//        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
//        onDispose {
//            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
//        }
//    }

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

//    LaunchedEffect(Unit) {
//        if (LocationPermissionsChecker.isLocationPermissionGranted(context = activity).not()) {
//            multiplePermissionResultLauncher.launch(permissionsToRequest)
//        }
//    }
//
//    dialogQueue
//        .reversed()
//        .forEach { permission ->
//            // Show dialog only if location permission is not granted
//            if (LocationPermissionsChecker.isLocationPermissionGranted(context = activity).not()
//            ) {
//                PermissionDialog(
//                    permissionTextProvider = when (permission) {
//                        android.Manifest.permission.ACCESS_FINE_LOCATION -> {
//                            LocationPermissionTextProvider()
//                        }
//
////                    android.Manifest.permission.CAMERA -> {
////                        CameraPermissionTextProvider()
////                    }
////
////                    android.Manifest.permission.ACCESS_NOTIFICATION_POLICY -> {
////                        NotificationsPermissionTextProvider()
////                    }
//
//                        else -> return@forEach
//                    },
//                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
//                        activity, permission
//                    ),
//                    onDismiss = viewModel::dismissDialog,
//                    onOkClick = {
//                        viewModel.dismissDialog()
//                        multiplePermissionResultLauncher.launch(
//                            arrayOf(permission)
//                        )
//                    },
//                    onGoToAppSettingsClick = activity::openAppSettings
//                )
//            }
//        }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Image with space at the bottom
        Image(
            painter = painterResource(R.drawable.splash_bg),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .offset {
                    IntOffset(x = 0, y = (-60))
                }// This moves the image up slightly, so text fits at the bottom
        )

        // Column for the text at the bottom
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 38.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.coruier_is_became_esay),
                style = CompactTypography.headlineLarge.copy(fontSize = 26.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            Text(
                text = stringResource(R.string.in_your_hand),
                style = CompactTypography.headlineMedium.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
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
//    SplashScreen(splashDefaultState())
}