package com.nat.bless.screens.dealingProducts.peresentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.screens.dealingProducts.models.Data
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun DealingProductItem(
    item: Data? = null,
    onClicked: (() -> Unit)? = null
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
//            Image(
//                painter = painterResource(R.drawable.splash_bg),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(84.dp)
//                    .clip(
//                        RoundedCornerShape(12.dp)
//                    ),
//                contentScale = ContentScale.Crop
//            )


            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                Row {
//                    Text(
//                        "الخميس 20 سبتمبر 2022",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
//                    )
//
//                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
//                    Text(
//                        text = "اسم المنتج",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                    )
                    Text(
                        text = item?.name.orEmpty(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }

//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "الكمية",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                    )
//                    Text(
//                        text = "1 كرتونة ",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
//                    )
//                }

                item?.uom_prices?.forEach {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = it.uom_name,
                            style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                        )
                        Text(
                            text = it.price.toString() + " EGP",
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 12.sp,
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
private fun ProductListItemP() {
    DealingProductItem()

}