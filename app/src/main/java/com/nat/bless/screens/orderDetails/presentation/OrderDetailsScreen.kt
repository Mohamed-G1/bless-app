package com.nat.bless.screens.orderDetails.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun OrderDetailsScreen(
    orderId: Int,
    state: OrderDetailsState,
    events: ((OrderDetailsEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    navigateToReturn: ((Int) -> Unit)? = null
) {


    LaunchedEffect(orderId) {
        events?.invoke(OrderDetailsEvents.OrderIdChanged(orderId))
    }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp).
            verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "تفاصيل الطلب",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "رقم الطلب",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.name.orEmpty(),
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "تاريخ الطلب",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.date_order.orEmpty(),
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }
                }
            }




            Spacer(Modifier.height(12.dp))


//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight(),
//                colors = CardDefaults.cardColors(containerColor = White),
//                border = BorderStroke(1.dp, color = WhiteGray)
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(
//                        text = "اسم الشركة",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
//                    )
//                    Spacer(Modifier.height(8.dp))
//                    Text(
//                        text = "اسم الطلب",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
//                    )
//
//                    Spacer(Modifier.height(8.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "1200 EGP",
//                            style = CompactTypography.headlineLarge.copy(
//                                fontSize = 14.sp,
//                                color = Gray
//                            )
//                        )
//
//                        Text(
//                            text = "5 (كرتونة)",
//                            style = CompactTypography.headlineLarge.copy(
//                                fontSize = 14.sp,
//                                color = Gray
//                            )
//                        )
//                    }
//
//                }
//            }

            state.model?.order_lines?.forEach {item ->
                OrderDetailsItem(item = item)
            }
            Spacer(Modifier.height(24.dp))

            Spacer(Modifier.weight(1f))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Text(
                    "تفاصيل الدفع",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )
                Spacer(Modifier.height(16.dp))
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
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "الاجمالي",
                                style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                            )
                            Text(
                                text = state.model?.amount_untaxed.toString() + " EGP",
                                style = CompactTypography.headlineLarge.copy(
                                    fontSize = 14.sp,
                                    color = Gray
                                )
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "ضريبة",
                                style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                            )
                            Text(
                                text = state.model?.amount_tax.toString() + " EGP",
                                style = CompactTypography.headlineLarge.copy(
                                    fontSize = 14.sp,
                                    color = Gray
                                )
                            )
                        }

                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "الاجمالي + الضريبة",
                                style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                            )
                            Text(
                                text = state.model?.amount_total.toString() + " EGP",
                                style = CompactTypography.headlineLarge.copy(
                                    fontSize = 14.sp,
                                    color = Gray
                                )
                            )
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                AppButton(
                    modifier = Modifier
                        .fillMaxWidth(.8f)
                        .align(Alignment.CenterHorizontally),
                    text = "مرتجع",
                    onClick = {
                        navigateToReturn?.invoke(state.model?.id ?: 0)
                    }
                )
            }

        }





    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.loading) {
        FullLoading()
    }
}

@Preview(showBackground = true)
@Composable
private fun OrderDetailsScreenPreview() {
    OrderDetailsScreen(orderId = 0, state = OrderDetailsState())
}