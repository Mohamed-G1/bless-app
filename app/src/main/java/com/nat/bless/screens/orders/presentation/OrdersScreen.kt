package com.nat.bless.screens.orders.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.textField.AppTextField
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.MediumGray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun OrdersScreen(
    state: OrdersState,
    events: ((OrdersEvents) -> Unit)? = null,

    onOrderClicked: ((Int) -> Unit)? = null
) {
    var tabIndex by remember { mutableStateOf(1) }
    val tabs = listOf("المرتجع", "الطلبات")

    LaunchedEffect(Unit) {
        events?.invoke(OrdersEvents.GetOrdersEvent)
    }

    var orderSearchQuery by remember { mutableStateOf("") }
    var returnSearchQuery by remember { mutableStateOf("") }

    // Filtered list - recomputed only when query or customers change
    val filteredOrders = remember(orderSearchQuery, state.model) {
        if (orderSearchQuery.isBlank()) {
            state.model
        } else {
            state.model.filter { customer ->
                customer.customer_id.name.contains(orderSearchQuery, ignoreCase = true)
                // Add more fields if needed, e.g.:
                // || customer.phone.contains(searchQuery, ignoreCase = true)
                // || customer.code.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val filteredReturns = remember(orderSearchQuery, state.returnsModel) {
        if (orderSearchQuery.isBlank()) {
            state.returnsModel
        } else {
            state.returnsModel.filter { customer ->
                customer.name.contains(orderSearchQuery, ignoreCase = true)
                // Add more fields if needed, e.g.:
                // || customer.phone.contains(searchQuery, ignoreCase = true)
                // || customer.code.contains(searchQuery, ignoreCase = true)
            }
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp),
    ) {
        Text(
            "الطلبات",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )
        Spacer(Modifier.height(16.dp))


        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = WhiteGray,
            indicator = {},
            modifier = Modifier
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


        if (tabIndex == 1) {
            Spacer(Modifier.height(16.dp))

            OrdersSearchField(
                query = orderSearchQuery,
                onQueryChange = { orderSearchQuery = it }
            )


            Spacer(Modifier.height(16.dp))

        } else {
            Spacer(Modifier.height(16.dp))

            ReturnsSearchField (
                query = returnSearchQuery,
                onQueryChange = { returnSearchQuery = it }
            )


            Spacer(Modifier.height(16.dp))
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {


                when (tabIndex){
                    0 -> {

                        if (filteredReturns.isEmpty()) {
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

                            itemsIndexed(items = filteredReturns) { index, item ->
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
                        if (filteredOrders.isEmpty()) {
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
                            itemsIndexed(items = filteredOrders) { index, item ->
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

//    LaunchedEffect(state.error) {
//        if (state.error.isNotEmpty()) {
//            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
//        }
//    }


    if (state.isLoading) {
        FullLoading()
    }
}
@Composable
private fun OrdersSearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = query,
        onValueChange = onQueryChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { /* keyboard will close automatically */ }
        ),
        trailingCompose = {
            if (query.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MediumGray,
                    contentDescription = ""
                )
            } else {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MediumGray,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}


@Composable
private fun ReturnsSearchField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = query,
        onValueChange = onQueryChange,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { /* keyboard will close automatically */ }
        ),
        trailingCompose = {
            if (query.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.Search,
                    tint = MediumGray,
                    contentDescription = ""
                )
            } else {
                IconButton(
                    onClick = { onQueryChange("") }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MediumGray,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

@Preview
@Composable
private fun OrderScreenP() {
    OrdersScreen(OrdersState())
}