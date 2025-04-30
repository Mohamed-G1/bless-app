package com.nat.greco.application.base.presentation.otp

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpSingleDigit(
    text: String,
    textSize: TextUnit = 18.sp,
    boxSize: Dp = 45.dp,
    hasFocus: Boolean = false,
    borderFocusColor: Color = MaterialTheme.colorScheme.primary,
    borderUnFocusColor: Color = Color.LightGray,
    textFocusColor: Color = MaterialTheme.colorScheme.primary,
    textUnFocusColor: Color = Color.Black
) {

    Box(
        modifier = Modifier
            .size(boxSize)
            .border(
                width = if (hasFocus) 2.dp else 1.dp,
                color = if (hasFocus) borderFocusColor else borderUnFocusColor,
                shape = RoundedCornerShape(12.dp),
            ), contentAlignment = Alignment.Center
    ) {
        val color = if (hasFocus) textFocusColor else textUnFocusColor
        Text(text = text, fontSize = textSize, color = color)
    }
}

@Preview
@Composable
fun OtpSingleViewPreview() {
    OtpSingleDigit("2")
}

@Preview
@Composable
fun OtpSingleViewPreview2() {
    OtpSingleDigit("2", hasFocus = true)
}