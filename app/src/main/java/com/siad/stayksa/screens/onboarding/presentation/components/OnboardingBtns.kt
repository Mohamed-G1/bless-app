package com.siad.stayksa.screens.onboarding.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.siad.stayksa.R
import com.siad.stayksa.base.ui.button.AppButton
import com.siad.stayksa.ui.theme.DarkGreen
import com.siad.stayksa.ui.theme.LightGray
import com.siad.stayksa.ui.theme.StayKsaTheme

@Composable
fun NextOnboardingBtn(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
) {
    AppButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.next),
        buttonColor = DarkGreen,
        textColor = Color.White,
        onClick= {onClick?.invoke()}
    )
}

@Composable
fun SkipBtn(modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    AppButton(
        modifier = modifier.fillMaxWidth(),
        text = stringResource(id = R.string.skip),
        buttonColor = LightGray,
        textColor = DarkGreen,
        onClick= {onClick?.invoke()}
    )
}


@Preview(showBackground = false)
@Composable
fun OnboardingBtnPreview() {
    StayKsaTheme {
        Column {
            NextOnboardingBtn()
            SkipBtn()
        }
    }
}