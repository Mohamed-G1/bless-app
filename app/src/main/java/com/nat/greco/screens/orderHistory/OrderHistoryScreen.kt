package com.nat.greco.screens.orderHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.routeDetails.domain.models.OrderLine
import com.nat.greco.screens.orders.OrderItem
import com.nat.greco.ui.theme.CompactTypography

@Composable
fun OrderHistoryScreen(
    state: OrderHistoryState,
    events: ((OrderHistoryEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    onOrderClicked: ((
        List<OrderLine>,
        String,
        String,
        String,
        String,
        String,
    ) -> Unit)? = null
) {

    LaunchedEffect(Unit) {
        events?.invoke(OrderHistoryEvents.GetOrderHistory)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp),
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "الطلبات السابقة",
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

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(items = state.model) { index, item ->
                OrderItem(
                    onClicked = { order ->
                        onOrderClicked?.invoke(
                            order.order_lines,
                            item.date_order,
                            item.name,
                            item.amount_untaxed.toString(),
                            item.amount_tax.toString(),
                            item.amount_total.toString()
                        )
                    },
                    item = item
                )
            }
        }
    }

    if (state.errorMessage?.isNotEmpty() == true) {
        ShowToast(state.errorMessage)
    }

    if (state.isLoading) {
        FullLoading()
    }
}