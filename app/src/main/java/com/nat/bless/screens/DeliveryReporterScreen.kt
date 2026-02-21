package com.nat.bless.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.ui.theme.CompactTypography

@Composable
fun DeliveryReporterScreen(
    onBackClicked: (() -> Unit)? = null

) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp).safeContentPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "تقرير المندوب",
                style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { onBackClicked?.invoke() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null
                )
            }
        }


        Spacer(Modifier.height(24.dp))

        val text1 = StringBuilder()
            .append("زيارات إيجابية: ")
            .append(0)
            .toString()
        Text(
            text1,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )
        Spacer(Modifier.height(12.dp))

        val text2 = StringBuilder()
            .append("زيارات ملغاة: ")
            .append(0)
            .toString()
        Text(
            text2,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )

        Spacer(Modifier.height(12.dp))

        val text3 = StringBuilder()
            .append("إجمالي الزيارات: ")
            .append(0)
            .toString()
        Text(
            text3,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )
    }
}


@Preview
@Composable
private fun DeliveryReporterScreenPerview() {
    DeliveryReporterScreen()
}