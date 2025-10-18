package com.nat.greco.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.ui.theme.CompactTypography

@Composable
fun UnitOfMeasureSheet(
    onAddClicked: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "الكمية",
                style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Counter(onCounterChange = {}, modifier = Modifier .fillMaxWidth(.51f)
                .height(40.dp),)
        }

        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "الوحدة (كرتونة)",
                style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.weight(1f))

            Counter(onCounterChange = {}, modifier = Modifier  .fillMaxWidth(.6f)
                .height(40.dp),)
        }


        Spacer(Modifier.height(32.dp))
        AppButton(
            text = "اضافة",
            onClick = {
                onAddClicked?.invoke()
            }
        )

    }

}