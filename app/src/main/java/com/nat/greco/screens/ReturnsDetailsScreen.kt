package com.nat.greco.screens

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.MediumGray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ReturnsDetailsScreen( onBackClicked: (() -> Unit)? = null) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "تفاصيل المرتجع",
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
                            text = "Ad1111",
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
                            text = "الخميس 20 سبتمبر 2022",
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }
                }
            }




            Spacer(Modifier.height(24.dp))



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

            ReturnDetailsItem(
                onClicked = {},

                )
        }



        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).wrapContentHeight().padding(16.dp)
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
                            text = "1000 EGP",
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
                            text = "1000 EGP",
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
                            text = "2000 EGP",
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
                modifier = Modifier.fillMaxWidth(.8f).align(Alignment.CenterHorizontally),
                text = "تأكيد",
                onClick = {
//                    onReceiveClicked?.invoke()
                }
            )
        }
    }
}

@Composable
fun ReturnDetailsItem(
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

            Icon(painter = painterResource(R.drawable.ic_add_shopping_cart),
                contentDescription = "",
                tint = MediumGray,
                modifier = Modifier
                    .size(70.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
            )

            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row {
                    Text(
                        "الخميس 20 سبتمبر 2022",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "رقم الطلب",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = "Ad1111",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }



                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "اجمالي الطلب",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = "3100 EGP",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }


            }
        }

    }

}

@Preview
@Composable
private fun ReturnsDetailsScreenPreview() {
    ReturnsDetailsScreen()
}