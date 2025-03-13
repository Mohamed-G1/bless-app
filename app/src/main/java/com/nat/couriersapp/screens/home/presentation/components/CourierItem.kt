package com.nat.couriersapp.screens.home.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.couriersapp.R
import com.nat.couriersapp.base.ui.shimmer.shimmerLoading
import com.nat.couriersapp.screens.home.domain.models.HomeModel
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.DeliverGreen
import com.nat.couriersapp.ui.theme.Gray
import com.nat.couriersapp.ui.theme.NotDeliverRed
import com.nat.couriersapp.ui.theme.WhiteGray

@Composable
fun CourierItem(
    item: HomeModel? = null,
    modifier: Modifier = Modifier,
    onClick: ((HomeModel) -> Unit)? = null
) {
    Card(
        onClick = {
            if (item != null) {
                onClick?.invoke(item)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.6f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        stringResource(R.string.deliver_to),
                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
                    )

                    Text(
                        text = item?.shipperContactName.orEmpty(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }

                DeliveryStatus(status = item?.lastStatusName.orEmpty())

            }


            DeliveryStatusBar(status = item?.lastStatusName.orEmpty())

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(.5f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        stringResource(R.string.delive_date),
                        style = CompactTypography.labelMedium.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.waybillPickupDate.orEmpty(),
                        style = CompactTypography.labelMedium.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Column(
                    modifier = Modifier.weight(.5f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    Text(
                        stringResource(R.string.delive_time),
                        style = CompactTypography.labelMedium.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item?.lastStatusUpdatedTime.orEmpty(),
                        style = CompactTypography.labelMedium.copy(fontSize = 12.sp, color = Gray)
                    )
                }
                Column(
                    modifier = Modifier.weight(.5f),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        stringResource(R.string.delive_mony),
                        style = CompactTypography.labelMedium.copy(fontSize = 12.sp)
                    )
                    val amount = StringBuilder()
                        .append(item?.collectCharges.toString())
                        .append(" ")
                        .append(stringResource(R.string.egp))
                        .toString()
                    Text(
                        text = amount,
                        style = CompactTypography.labelMedium.copy(fontSize = 12.sp, color = Gray)
                    )
                }

            }
        }
    }
}


@Composable
fun DeliveryStatus(status: String) {
    val color = when (status) {
        "Delivered" -> DeliverGreen
        "Not Delivered" -> NotDeliverRed
        else -> Gray
    }
    Card(
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),

        ) {

        Text(
            status,
            style = CompactTypography.labelMedium.copy(
                color = WhiteGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()

        )

    }
}

@Composable
fun DeliveryStatusBar(status: String) {
    val color = when (status) {
        "Delivered" -> DeliverGreen
        "Not Delivered" -> NotDeliverRed
        else -> Gray
    }

    val icon = when (status) {
        "Delivered" -> painterResource(R.drawable.ic_check_circle)
        "Not Delivered" -> painterResource(R.drawable.ic_cancel)
        else -> painterResource(R.drawable.ic_filled_circle)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp), contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(thickness = 2.dp, color = color.copy(alpha = .2f), modifier = Modifier.padding(horizontal = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filled_circle),
                contentDescription = null,
                tint = color
            )

            Icon(
                painter = painterResource(R.drawable.ic_filled_circle),
                contentDescription = null,
                tint = color
            )

            Icon(
                painter = icon,
                contentDescription = null,
                tint = color,
            )
        }
    }

}

@Preview(locale = "ar")
@Composable
private fun CourierItemPreview() {
    CourierItem()
}

@Preview(locale = "ar")
@Composable
private fun DeliveryStatusPreview() {
    DeliveryStatus("Gamal")
}

@Preview(locale = "ar")
@Composable
private fun DeliveryStatusBarPreview() {
    DeliveryStatusBar("Gamal")
}