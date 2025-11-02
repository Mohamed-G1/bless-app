package com.nat.greco.screens.home.presentation.components

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.screens.home.domain.models.Route
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DeliverGreen
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.NotDeliverRed
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ListItem(
    item: Route? = null,
    modifier: Modifier = Modifier,
    onClick: ((Route) -> Unit)? = null
) {
val context = LocalContext.current
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
                        "العميل",
                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
                    )

                    Text(
                        text =  item?.customer_id?.name.orEmpty() ,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }

                DeliveryStatus(is_visited = item?.is_visited == true,
                    is_collected = item?.is_collected == true
                )

            }


            DeliveryStatusBar(item?.is_visited == true)

            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    IconButton(onClick = {

                        val uri =
                            Uri.parse("geo:0,0?q=${item?.customer_id?.address}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.google.android.apps.maps") // Explicitly use Google Maps app
                        context.startActivity(intent)

                    }) {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = null
                        )
                    }
                    Text("الموقع", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    IconButton(onClick = {
                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${item?.customer_id?.phone}")
                        )
                        context.startActivity(intent)

                    }){
                        Image(
                            painter = painterResource(R.drawable.ic_call),
                            contentDescription = null
                        )
                    }

                     Text("مكالمة", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))

                }


                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    IconButton(onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data =
                                    Uri.parse("https://wa.me/${item?.customer_id?.phone}") // WhatsApp URL with phone number
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Handle exception if WhatsApp is not installed or other issues
                            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }) {
                        Image(
                            painter = painterResource(R.drawable.ic_whats),
                            contentDescription = null
                        )
                    }
                    Text("واتساب", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
                }

            }
        }
    }
}


@Composable
fun DeliveryStatus(is_visited: Boolean,is_collected: Boolean ) {
    val color = when (is_visited) {
        true -> DeliverGreen
        false -> Gray
    }

    val visitedStatus = if (is_visited) "تم الزيارة" else "قيد التنفيذ"
//    val collectedStatus = if (is_collected) "تم التحصيل" else "لم يتم التحصيل

    Card(
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),

        ) {

        Text(
            visitedStatus,
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
fun DeliveryStatusBar(is_visited: Boolean) {
    val color = when (is_visited) {
        true -> DeliverGreen
        false -> Gray
    }

    val icon = when (is_visited) {
        true -> painterResource(R.drawable.ic_check_circle)
        false -> painterResource(R.drawable.ic_check_circle)
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

//            Icon(
//                painter = painterResource(R.drawable.ic_filled_circle),
//                contentDescription = null,
//                tint = color
//            )

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
private fun ListItemPreview() {
    ListItem()
}

@Preview(locale = "ar")
@Composable
private fun DeliveryStatusPreview() {
    DeliveryStatus(true, true)
}

@Preview(locale = "ar")
@Composable
private fun DeliveryStatusBarPreview() {
    DeliveryStatusBar(true)
}