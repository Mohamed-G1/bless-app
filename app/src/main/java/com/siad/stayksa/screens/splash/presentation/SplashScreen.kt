package com.siad.stayksa.screens.splash.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.siad.stayksa.R
import com.siad.stayksa.ui.theme.CompactTypography

@Composable
fun SplashScreen(
    state: SplashState, navigateToNext: (() -> Unit)? = null,
) {
    if (state.shouldNavigate) {
        LaunchedEffect(Unit) {
            navigateToNext?.invoke()
        }
    }
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Image(
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.splash_img),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.welcome_to),
                    style = CompactTypography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Medium
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.hande_shak_img),
                    contentDescription = null
                )

            }

            Image(
                painter = painterResource(id = R.drawable.stay_ksa_logo),
                contentDescription = null
            )

            Text(
                text = stringResource(R.string.we_will_help_you_find_a_best_place_to_stay_travel_as_you_want_and_where_you_want),
                style = CompactTypography.bodyMedium.copy(color = Color.White),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }
    }
}

@Preview
@Preview(locale = "ar")
@Composable
private fun SplashViewPreview() {
    SplashScreen(splashDefaultState())
}