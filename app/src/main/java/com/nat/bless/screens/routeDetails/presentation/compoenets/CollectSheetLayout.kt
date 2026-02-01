package com.nat.bless.screens.routeDetails.presentation.compoenets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.ui.theme.CompactTypography

@Composable
fun CollectSheetLayout() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {

        Text(
            text = "تحصيل",
            style = CompactTypography.labelMedium.copy(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(38.dp))

    }
}