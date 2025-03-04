package com.nat.couriersapp.screens.home.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.LightGray
import com.nat.couriersapp.ui.theme.MediumBlue
import com.nat.couriersapp.ui.theme.MediumGray

@Composable
fun SearchTapItem(
    query: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val cardColor = if (isSelected) MediumBlue else LightGray
    val textColor = if (isSelected) Color.White else Color.DarkGray.copy(alpha = .4f)
    Card(
        onClick = { onClick.invoke() },
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
    ) {
        Text(
            text = query,
            style = CompactTypography.labelMedium.copy(fontSize = 13.sp, color = textColor),
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Preview
@Composable
private fun SearchTapItemPreview() {
    SearchTapItem("Gamal", true, {})
}