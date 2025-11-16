package com.nat.greco.screens.login.presentation

import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nat.greco.R
import com.nat.greco.base.locationChecker.LocationHandler
import com.nat.greco.base.locationChecker.LocationPermissionsChecker
import com.nat.greco.base.permissions.LocationPermissionTextProvider
import com.nat.greco.base.permissions.PermissionDialog
import com.nat.greco.base.permissions.PermissionViewModel
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.routeDetails.presentation.RouteDetailsEvents
import com.nat.greco.screens.splash.presentation.openAppSettings
import com.nat.greco.ui.theme.CompactTypography
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun LoginScreen(
    state: LoginState,
    events: ((LoginEvents) -> Unit)? = null,
    navigateToHome: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    var isFetchingLocation by remember { mutableStateOf(false) }
    var isLocationGranted by remember {
        mutableStateOf(LocationPermissionsChecker.isLocationPermissionGranted(activity))
    }
    // --- NEW: Fetch location when permission is granted ---
    LaunchedEffect(isLocationGranted) {
        if (isLocationGranted && LocationHandler.isLocationServiceEnabled(context)) {
            isFetchingLocation = true
            // Assuming LocationHandler can be updated to use a callback or Flow
            // For now, we'll keep the coroutine structure
            val location = LocationHandler.getCurrentLocation(context)
            if (location != null) {
                // NEW: Store location in the ViewModel state
                events?.invoke(LoginEvents.LocationFetched(
                    lat = location.latitude.toString(),
                    long = location.longitude.toString()
                ))
            }
            isFetchingLocation = false
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isLocationGranted = LocationPermissionsChecker.isLocationPermissionGranted(activity)
            }

        }
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    val permissionsToRequest = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
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

            }
        }
    )

    LaunchedEffect(Unit) {
        if (LocationPermissionsChecker.isLocationPermissionGranted(context = activity).not()) {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
        }
    }


    dialogQueue
        .reversed()
        .forEach { permission ->
            // Show dialog only if location permission is not granted
            if (LocationPermissionsChecker.isLocationPermissionGranted(context = activity).not()
            ) {
                PermissionDialog(
                    permissionTextProvider = when (permission) {
                        android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                            LocationPermissionTextProvider()
                        }

                        else -> return@forEach
                    },
                    isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                        activity, permission
                    ),
                    onDismiss = viewModel::dismissDialog,
                    onOkClick = {
                        viewModel.dismissDialog()
                        multiplePermissionResultLauncher.launch(
                            arrayOf(permission)
                        )
                    },
                    onGoToAppSettingsClick = activity::openAppSettings
                )
            }
        }


    // List of image resources
    val images = listOf(
        painterResource(id = R.drawable.animate_1),
        painterResource(id = R.drawable.animate_2),
        painterResource(id = R.drawable.animate_3)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 32.dp)
                .size(100.dp)
        )

        Spacer(Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.wellcom),
            style = CompactTypography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(24.dp))

        PhoneNumberTextField(state, events)
        Spacer(Modifier.height(12.dp))
        PasswordTextField(state, events)
        // --- NEW: Add a Row with a small indicator ---\
        Spacer(Modifier.height(12.dp))
        if (isFetchingLocation) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    strokeWidth = 2.dp,
                    color = Color.Gray
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    text = "جار الحصول علي بيانات الموقع",
                    color = Color.Gray,
                    style = CompactTypography.bodyMedium
                )
            }
        }
        Spacer(Modifier.height(48.dp))


        AppButton(
            text = stringResource(R.string.login),
            enabled = !isFetchingLocation,
            onClick = {
                // The location should already be in the 'state' from the ViewModel
                if (state.lat != null && state.lang != null) {
                    // API call is now INSTANT because the location is ready
                    events?.invoke(
                        LoginEvents.SubmitUser(
                            phone = state.mobile.orEmpty(),
                            password = state.password.orEmpty()
                        )
                    )
                } else {
                    // Handle the case where location is not yet available or permission is off
                    Toast.makeText(
                        context,
                        "يرجي تشغيل خدمات الموقع",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Optionally, re-trigger permission request if needed
                    if (!isLocationGranted) {
                        multiplePermissionResultLauncher.launch(permissionsToRequest)
                    }
                }
            }
        )

        Spacer(Modifier.height(64.dp))

        AnimatedImage(images = images)

    }


    if (state.navigateToHome) {
        LaunchedEffect(Unit) {
            navigateToHome?.invoke()
            events?.invoke(LoginEvents.NavigationComplete)
        }
    }

    if (state.errorMessage?.isNotEmpty() == true) {
        ShowToast(state.errorMessage)
    }
    if (state.isLoading) {
        FullLoading()
    }

}

@Composable
fun AnimatedImage(images: List<Painter>) {
    // Current image index
    var currentImageIndex by remember { mutableIntStateOf(0) }

    // Launch the infinite loop with a delay of 1 second for each image
    LaunchedEffect(Unit) {
        while (true) {
            delay(500)
            currentImageIndex = (currentImageIndex + 1) % images.size
        }
    }
    // Animate alpha for smooth transition between images
    val alpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = 300,
            delayMillis = 50,
            easing = LinearOutSlowInEasing
        ),
        label = "",
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Display the image with the animated alpha value
        Image(
            painter = images[currentImageIndex],
            contentDescription = null,
            modifier = Modifier
                .alpha(alpha)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
    }
}


@Composable
private fun PhoneNumberTextField(
    state: LoginState,
    event: ((LoginEvents) -> Unit)?,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = stringResource(R.string.enter_your_phone),
        label = stringResource(id = R.string.phone),
        isError = !state.isValidMobile,
        errorMessage = if (state.isValidMobile) null else stringResource(id = state.mobileValidationMessage),
        value = state.mobile.orEmpty(),
        onValueChange = {
            event?.invoke(LoginEvents.PhoneNumberChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        )
    )
}


@Composable
private fun PasswordTextField(
    state: LoginState,
    event: ((LoginEvents) -> Unit)?,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = stringResource(R.string.enter_your_password),
        label = stringResource(R.string.password),
        isError = !state.isValidPassword,
        errorMessage = if (state.isValidPassword) null else stringResource(id = state.passwordValidationMessage),
        value = state.password.orEmpty(),
        isPassword = true,
        onValueChange = {
            event?.invoke(LoginEvents.PasswordChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        )
    )
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(locale = "ar")
@Composable
private fun LoginScreenPreview() {
    LoginScreen(LoginState())
}