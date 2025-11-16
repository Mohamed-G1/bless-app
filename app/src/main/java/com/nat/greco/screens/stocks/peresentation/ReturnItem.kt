package com.nat.greco.screens.stocks.peresentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.base.ui.imageLoader.AppImageLoading
import com.nat.greco.screens.stocks.models.returnsModel.ReturnedListData
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.WhiteGray

@Composable
 fun ReturnItem(
    item: ReturnedListData? = null,
    onClicked: (() -> Unit)? = null
) {
    Card(
//        onClick = { onClicked?.invoke() },
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
            AppImageLoading(
                imgUrl = item?.reference.orEmpty(),
                modifier = Modifier
                    .size(84.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Crop
            )



            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Text(
//                    text = "اسم المنتج",
//                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                )
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
                        text = "الكمية",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.quantity.toString(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp),

                        )
                }

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
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }
            }

        }

    }
}
