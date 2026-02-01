package com.nat.bless.screens.orderDetails.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.screens.orders.domain.models.OrdersLine
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.Orange
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun OrderDetailsItem(
    item: OrdersLine? = null
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            if (item?.is_reward_line == true) {
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

            val padding = if (item?.is_reward_line == true) 28.dp else 12.dp

            Column(
                modifier = Modifier
                    .fillMaxWidth().padding(top = padding)
                    .padding(12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "اسم المنتج",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.product_id?.name.orEmpty(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "كود المنتج",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.product_id?.reference.orEmpty(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "سعر الوحدة",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.price_unit.toString() + " EGP",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "الكمية",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.product_uom_qty.toString() + " " + item?.product_uom,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "نسبة الخصم",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.discount.toString() + " %",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "اجمالي الخصم",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.discount_amount.toString() + " EGP",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "الاجمالي",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.price_subtotal.toString() + " EGP",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }


            }
        }

    }

}

@Preview
@Composable
private fun OrderItemP() {
    OrderDetailsItem()
}