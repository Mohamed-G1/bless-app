package com.nat.greco.base.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DarkGreen

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String = "StayKsa",
    buttonColor: Color = DarkGreen,
    textColor: Color = Color.White,
    textSize: TextUnit = 16.sp,
    onClick: (() -> Unit)? = null
) {
    Surface(
        onClick = { onClick?.invoke() },
        modifier = modifier.fillMaxWidth(),
        color = buttonColor,
        shape = RoundedCornerShape(16.dp),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(12.dp),
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