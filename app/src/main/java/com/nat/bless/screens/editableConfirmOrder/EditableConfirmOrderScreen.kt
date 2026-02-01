package com.nat.bless.screens.editableConfirmOrder

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.NotDeliverRed
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun EditableConfirmOrderScreen(
    customerId: Int,
    state: EditableConfirmOrderState,
    events: ((EditableConfirmOrderEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    navigateToConfirmOrderScreen: ((Int, Int) -> Unit)? = null,
) {
    LaunchedEffect(customerId) {
        events?.invoke(EditableConfirmOrderEvents.CustomerIdChanged(customerId))
    }


    Column(
        modifier = Modifier.fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 16.dp)
            .verticalScroll(rememberScrollState())

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
            "تفاصيل الطلب",
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
                        state.model?.amount_tax.toString() + " EGP",
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
                        state.model?.amount_total.toString() + " EGP",
                        style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                    )
                }
            }
        }
        Spacer(Modifier.height(24.dp))


        state.model?.order_lines?.forEach { line ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {


                if (line.is_reward_line.not()){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        IconButton(
                            onClick = {
                                events?.invoke(EditableConfirmOrderEvents.DeleteItem(
                                    lineId = line.id
                                ))
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = NotDeliverRed,
                                contentDescription = null
                            )
                        }
                        Spacer(Modifier.height(2.dp))

                        Text(
                            "مسح",
                            style = CompactTypography.headlineMedium.copy(
                                fontSize = 12.sp, color =
                                    NotDeliverRed
                            )
                        )


                    }
                    Spacer(Modifier.width(14.dp))
                }

                EditableOrderListItem(item = line, onCounterChange = { newQty ->
                    events?.invoke(
                        EditableConfirmOrderEvents.UpdateItem(
                            counter = newQty,
                            lineId = line.id,
                            price = line.price_unit
                        )
                    )
                })

            }
        }


        Spacer(Modifier.height(24.dp))

        Spacer(Modifier.weight(1f))

        AppButton(
            enabled = state.model?.order_lines?.isNotEmpty() == true,
            text = "اكمال الطلب",
            onClick = {
                navigateToConfirmOrderScreen?.invoke(
                    state.model?.id ?: 0,
                    state.customerId,
                )
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

@Preview
@Composable
private fun CartScreenPreview() {
    EditableConfirmOrderScreen(0, EditableConfirmOrderState())
}