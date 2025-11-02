package com.nat.greco.screens.clients

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ClientItem(
//    item: HomeModel? = null,
){
    val context = LocalContext.current

//    Card(
//
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight(),
//        colors = CardDefaults.cardColors(containerColor = Color.White),
//        border = BorderStroke(2.dp, WhiteGray)
//    ) {
//
//
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .wrapContentHeight(),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//            ) {
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//
//
//                    Text(
//                        "العميل",
//                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
//                    )
//
//                    Text(
//                        text =  item?.consigneeName.orEmpty() ,
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                    )
//
//
//                }
//                Spacer(Modifier.weight(1f))
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//
//                ) {
//
//
//                    Text(
//                        "نوع العميل",
//                        style = CompactTypography.labelMedium.copy(
//                            color = Gray,
//                            fontSize = 12.sp
//                        )
//                    )
//
//                    Text(
//                        text = "premium",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                    )
//                }
//
//                Spacer(Modifier.weight(1f))
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(8.dp),
//                    horizontalAlignment = Alignment.End
//                ) {
//
//                    Text(
//                        "اخر تعامل",
//                        style = CompactTypography.labelMedium.copy(
//                            color = Gray,
//                            fontSize = 12.sp
//                        )
//                    )
//
//                    Text(
//                        text = "12/2/2024",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
//                    )
//                }
//
////                DeliveryStatus(status = item?.lastStatusName.orEmpty())
//
//            }
//
//
////            DeliveryStatusBar(status = item?.lastStatusName.orEmpty())
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//
//
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//
//                ) {
//                    IconButton(onClick = {
//
//                        val uri =
//                            Uri.parse("geo:0,0?q=${item?.consigneeDestinationAddress}")
//                        val intent = Intent(Intent.ACTION_VIEW, uri)
//                        intent.setPackage("com.google.android.apps.maps") // Explicitly use Google Maps app
//                        context.startActivity(intent)
//
//                    }) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_location),
//                            contentDescription = null
//                        )
//                    }
//                    Text("الموقع", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
//                }
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//
//                ) {
//                    IconButton(onClick = {
//                        val intent = Intent(
//                            Intent.ACTION_DIAL,
//                            Uri.parse("tel:${item?.consigneePhone}")
//                        )
//                        context.startActivity(intent)
//
//                    }){
//                        Image(
//                            painter = painterResource(R.drawable.ic_call),
//                            contentDescription = null
//                        )
//                    }
//
//                    Text("مكالمة", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
//
//                }
//
//
//                Column(
//                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//
//                    IconButton(onClick = {
//                        try {
//                            val intent = Intent(Intent.ACTION_VIEW).apply {
//                                data =
//                                    Uri.parse("https://wa.me/${item?.consigneePhone}") // WhatsApp URL with phone number
//                            }
//                            context.startActivity(intent)
//                        } catch (e: Exception) {
//                            // Handle exception if WhatsApp is not installed or other issues
//                            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    }) {
//                        Image(
//                            painter = painterResource(R.drawable.ic_whats),
//                            contentDescription = null
//                        )
//                    }
//                    Text("واتساب", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
//                }
//
//            }
//        }
//    }
}

@Preview
@Composable
private fun ClientItemP() {
    ClientItem()
}