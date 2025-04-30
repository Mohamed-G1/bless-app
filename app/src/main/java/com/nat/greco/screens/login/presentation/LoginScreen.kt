package com.nat.greco.screens.login.presentation

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.textField.AppTextField
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.ui.theme.CompactTypography
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    state: LoginState,
    events: ((LoginEvents) -> Unit)? = null,
    navigateToHome: (() -> Unit)? = null
) {

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

        UserNameTextField(state, events)
        Spacer(Modifier.height(12.dp))
        PasswordTextField(state, events)

        Spacer(Modifier.height(48.dp))


        AppButton(
            text = stringResource(R.string.login),
            onClick = {
                events?.invoke(LoginEvents.SubmitUser)
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
private fun UserNameTextField(
    state: LoginState,
    event: ((LoginEvents) -> Unit)?,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = stringResource(R.string.enter_your_username),
        label = stringResource(id = R.string.username),
        isError = !state.isValidUserName,
        errorMessage = if (state.isValidUserName) null else stringResource(id = state.userNameValidationMessage),
        value = state.userName.orEmpty(),
        onValueChange = {
            event?.invoke(LoginEvents.UserNameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
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

@Preview(locale = "ar")
@Composable
private fun LoginScreenPreview() {
    LoginScreen(LoginState())
}