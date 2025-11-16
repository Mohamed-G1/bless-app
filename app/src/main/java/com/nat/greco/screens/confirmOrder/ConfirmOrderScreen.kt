package com.nat.greco.screens.confirmOrder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.orderDetails.presentation.OrderDetailsItem
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ConfirmOrderScreen(
    id: Int,
    state: ConfirmOrderState,
    events: ((ConfirmOrderEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null
) {

    LaunchedEffect(id) {
        events?.invoke(ConfirmOrderEvents.OrderIdChanged(id))
    }


    Column(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "تأكيد الطلب",
                style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { onBackClicked?.invoke() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "تفاصيل الدفع",
            style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
        )
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(1.dp, color = WhiteGray)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "اجمالي",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )

                    Text(
                        state.model?.amount_untaxed.toString() + " EGP",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "ضريبة",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )

                    Text(
                        state.model?.amount_untaxed.toString() + " EGP",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )
                }

                Spacer(Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "اجمالي + ضريبة",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )

                    Text(
                        state.model?.amount_untaxed.toString() + " EGP",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.model?.order_lines ?: listOf()) { item ->
                OrderDetailsItem(item = item)
            }
        }

        Spacer(Modifier.weight(1f))

        AppButton(
            text = "تأكيد الطلب",
            onClick = {
                events?.invoke(ConfirmOrderEvents.ConfirmOrder)
            },
        )
    }

    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }

}