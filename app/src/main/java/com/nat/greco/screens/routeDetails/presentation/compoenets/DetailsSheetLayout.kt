package com.nat.greco.screens.routeDetails.presentation.compoenets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.ui.theme.MediumBlue

@Composable
fun DetailsSheetLayout(
    onNewOrderClicked: (() -> Unit)? = null,
    onCollectClicked: (() -> Unit)? = null
) {

    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
    ) {


        AppButton(
            text = "طلب",
            onClick = {
                onNewOrderClicked?.invoke()
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppButton(
            text = "تحصيل",
            buttonColor = Color.White,
            boarderColor = MediumBlue,
            textColor = MediumBlue,
            onClick = {
                onCollectClicked?.invoke()
            }

        )

        Spacer(Modifier.height(24.dp))
    }

}

@Preview
@Composable
private fun CollectSheetLayoutPreview() {
    CollectSheetLayout()
}