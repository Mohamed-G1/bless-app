package com.nat.bless.screens.orderHistory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Badge
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
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.Orange
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun OrderHistoryDetailsScreen(
    orderId : Int,
    state : OrderHistoryDetailsState,
    events: ((OrderHistoryDetailsEvents) -> Unit) ? =null,

    onBackClicked: (() -> Unit)? = null
) {

    LaunchedEffect(orderId) {
        events?.invoke(OrderHistoryDetailsEvents.OrderIdChanged(orderId))
    }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 16.dp).verticalScroll(
                    rememberScrollState()
                )
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


            Spacer(Modifier.height(24.dp))


            state.model?.order_lines?.forEach {item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    colors = CardDefaults.cardColors(containerColor = White),
                    border = BorderStroke(1.dp, color = WhiteGray)
                ) {

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        if (item.is_reward_line) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopStart)
                                    .padding(12.dp),
                                containerColor = Orange
                            ) {
                                Text(
                                    "Reward",
                                    style = CompactTypography.headlineLarge
                                        .copy(fontSize = 10.sp, color = White),
                                    modifier = Modifier.padding(4.dp)
                                )
                            }
                        }

                        val padding = if (item.is_reward_line) 28.dp else 12.dp

                        Column(
                            modifier = Modifier
                                .fillMaxWidth().padding(top = padding)
                                .padding(12.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "اسم الطلب",
                                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                                )

                                Text(
                                    text = item.product_id.name,
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
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
                                    text = "سعر الوحدة",
                                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                                )
                                Text(
                                    text = item.price_unit.toString() + " EGP",
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
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
                                    text = "الكمية",
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
                                    )
                                )

                                Text(
                                    text = item.product_uom_qty.toString() + " " + item.product_uom,
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
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
                                    text = "السعر",
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
                                    )
                                )

                                Text(
                                    text = item.price_subtotal.toString() + " EGP",
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
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
                                    text = "نسبة الخصم",
                                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                                )
                                Text(
                                    text = item.discount.toString() + " %",
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
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
                                    text = "اجمالي الخصم",
                                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                                )
                                Text(
                                    text = item.discount_amount.toString() + " EGP",
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
                                        color = Gray
                                    )
                                )
                            }

                        }


                    }                }
            }
//            LazyColumn(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//            ) {
//                items(state.model?.order_lines ?: listOf()) { item ->
//
//                }
//            }

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
                                text = state.model?.amount_untaxed.toString() +" EGP",
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
                                text = state.model?.amount_tax.toString() +" EGP",
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
                                text = state.model?.amount_total.toString() +" EGP",
                                style = CompactTypography.headlineLarge.copy(
                                    fontSize = 14.sp,
                                    color = Gray
                                )
                            )
                        }
                    }
                }
            }

        }




    }


@Preview
@Composable
private fun LastOrdersDetailsScreenP() {
    OrderHistoryDetailsScreen(0, state = OrderHistoryDetailsState())
}