package com.nat.greco.screens.accounts

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.screens.home.presentation.HomeState.Companion.dummyList
import com.nat.greco.screens.orders.OrderItem
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun AccountsScreen(
    onBackClicked: (() -> Unit)? = null
) {
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(top = 16.dp),
        ) {
            val context = LocalContext.current


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "الحسابات",
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
//                    if (courierType == CourierSheetTypes.waybill.name) {
//                        showWaybillSortBottomSheet = true
//                    } else {
//                        showPickupSortBottomSheet = true
//                    }
                }) {
                    Image(
                        painter = painterResource(R.drawable.ic_sort), contentDescription = null
                    )
                }

                Spacer(Modifier.width(24.dp))

                IconButton(onClick = {
//                    if (courierType == CourierSheetTypes.waybill.name) {
//                        showWaybillFilterBottomSheet = true
//                    } else {
//                        showPickupFilterBottomSheet = true
//                    }
                }) {
                    Image(
                        painter = painterResource(R.drawable.ic_filter), contentDescription = null
                    )
                }


            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                itemsIndexed(items = dummyList) { index, item ->
                    AccountListItem()
                }
            }
        }


        Column(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .padding(16.dp)) {
            Text(
                "تفاصيل الحساب",
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
                            text = "اجمالي المديونية",
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


                }
            }
        }
    }
}

@Preview
@Composable
private fun AccountsScreenPreview() {
    AccountsScreen()
}