package com.nat.couriersapp.base.ui.appButton

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.MediumBlue

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String = "Courier",
    buttonColor: Color = MediumBlue,
    textColor: Color = Color.White,
    boarderColor: Color = MediumBlue,
    textSize: TextUnit = 18.sp,
    onClick: (() -> Unit)? = null
) {
    Surface (
        onClick = { onClick?.invoke() },
        modifier = modifier.fillMaxWidth(),
        color = buttonColor,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, boarderColor)
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(16.dp),
            style = CompactTypography.headlineMedium.copy(
                color = textColor,
                textAlign = TextAlign.Center,
                fontSize = textSize
            )
        )
    }

}

@Preview
@Composable
private fun AppButtonPreview() {
    AppButton()
}