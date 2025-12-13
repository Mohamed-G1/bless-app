package com.nat.greco.screens.orders.presentation

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.screens.orders.domain.models.OrdersResponse
import com.nat.greco.screens.stocks.peresentation.StockEvents
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.MediumBlue
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun OrdersScreen(
    state: OrdersState,
    events: ((OrdersEvents) -> Unit)? = null,

    onOrderClicked: ((Int) -> Unit)? = null
) {
    var tabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("المرتجع", "الطلبات")
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        events?.invoke(OrdersEvents.GetOrdersEvent)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item{
                Spacer(Modifier.height(24.dp))

                Text(
                    "الطلبات",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )
                Spacer(Modifier.height(16.dp))
            }

            item {
                TabRow(
                    selectedTabIndex = tabIndex,
                    containerColor = WhiteGray,
                    indicator = {},
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = tabIndex == index
                        val textColor = if (selected) WhiteGray else Color.Black
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
                                        color = textColor
                                    )
                                )
                            },
                            selected = tabIndex == index,
                            onClick = {
                                if (index == 0) {
                                    events?.invoke(OrdersEvents.GetReturnsEvent)
                                } else {
                                    events?.invoke(OrdersEvents.GetOrdersEvent)
                                }

                                tabIndex = index

                            },
                            modifier = Modifier.background(
                                if (selected) MediumBlue else WhiteGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                        )
                    }
                }
            }



                when (tabIndex){
                    0 -> {

                        if (state.returnsModel.isEmpty() == true) {
                          item {
                              Column(
                                  modifier = Modifier.fillMaxSize(),
                                  verticalArrangement = Arrangement.Center,
                                  horizontalAlignment = Alignment.CenterHorizontally
                              ) {
                                  Spacer(Modifier.height(300.dp))
                                  Text(
                                      "لا يوجد مرتجع", style = CompactTypography.headlineMedium.copy(
                                          fontSize = 22.sp,
                                          textAlign = TextAlign.Center,
                                          fontWeight = FontWeight.Bold
                                      )
                                  )
                              }
                          }

                        }else {
                            itemsIndexed(items = state.returnsModel) { index, item ->
                                ReturnedItem(
                                    onClicked = { model ->
                                        onOrderClicked?.invoke(model.id)
                                    },
                                    item = item
                                )
                            }
                        }
                    }

                    1-> {
                        if (state.model.isEmpty() == true) {
                            item {

                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Spacer(Modifier.height(300.dp))

                                    Text(
                                        "لا يوجد طلبات", style = CompactTypography.headlineMedium.copy(
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }

                        }else {
                            itemsIndexed(items = state.model) { index, item ->
                                OrderItem(
                                    onClicked = { model ->
                                        onOrderClicked?.invoke(model.id)
                                    },
                                    item = item
                                )
                            }
                        }
                    }

            }












        }
    }



//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.End
//        ) {
//            IconButton(onClick = {
////                    if (courierType == CourierSheetTypes.waybill.name) {
////                        showWaybillSortBottomSheet = true
////                    } else {
////                        showPickupSortBottomSheet = true
////                    }
//            }) {
//                Image(
//                    painter = painterResource(R.drawable.ic_sort), contentDescription = null
//                )
//            }
//
//            Spacer(Modifier.width(24.dp))
//
//            IconButton(onClick = {
////                    if (courierType == CourierSheetTypes.waybill.name) {
////                        showWaybillFilterBottomSheet = true
////                    } else {
////                        showPickupFilterBottomSheet = true
////                    }
//            }) {
//                Image(
//                    painter = painterResource(R.drawable.ic_filter), contentDescription = null
//                )
//            }
//
//
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))

    LaunchedEffect(state.error) {
        if (state.error.isNotEmpty()) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }


    if (state.isLoading) {
        FullLoading()
    }
}

@Preview
@Composable
private fun OrderScreenP() {
    OrdersScreen(OrdersState())
}