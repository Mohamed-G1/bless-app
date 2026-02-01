package com.nat.bless.screens.receviceStock.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockResponse
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun ReceiveStockItem(
    item: ReceiveStockResponse? = null,
    onClicked: ((Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
//            AppImageLoading(
//                imgUrl = item?.product?.reference.orEmpty(),
//                modifier = Modifier
//                    .size(84.dp)
//                    .clip(
//                        RoundedCornerShape(12.dp)
//                    ),
//                contentScale = ContentScale.Crop
//            )
//


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "رقم الحركة",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                )
                Text(
                    text = item?.name.orEmpty(),
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                )
            }



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "نوع الشحنة",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                )
                Text(
                    text = item?.picking_type.toString(),
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp),

                    )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "المنتجات",
                modifier = Modifier.fillMaxWidth(),
                style = CompactTypography.headlineLarge.copy(fontSize = 16.sp, textAlign = TextAlign.Start)
            )
            Spacer(Modifier.height(4.dp))

            item?.lines?.forEach { line ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "اسم المنتج",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = line.product.name ,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }
                Spacer(Modifier.height(2.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "الكمية",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = "${line.quantity}" + " ${line.uom}",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }
//                Spacer(Modifier.height(4.dp))

//                Text(
//                    text = "الاسعار",
//                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                )
//
//                Spacer(Modifier.height(4.dp))
//
//                line.product.uom_prices.forEach {
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = it.uom_name,
//                            style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                        )
//                        Text(
//                            text = it.price.toString() + " EGP",
//                            style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                        )
//                    }
//                }
            }

            Spacer(Modifier.height(12.dp))

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .align(Alignment.CenterHorizontally),
                text = "تأكيد الاستلام",
                onClick = {onClicked?.invoke(item?.id ?: 0)}
            )

        }

    }
}
