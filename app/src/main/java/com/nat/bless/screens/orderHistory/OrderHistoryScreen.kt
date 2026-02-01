package com.nat.bless.screens.orderHistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.ui.theme.CompactTypography

@Composable
fun OrderHistoryScreen(
    customerId : Int,
    state: OrderHistoryState,
    events: ((OrderHistoryEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    onOrderClicked: ((
       Int
    ) -> Unit)? = null
) {

    LaunchedEffect(customerId) {
        events?.invoke(OrderHistoryEvents.GetOrderHistory(id = customerId))
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
                OrderHistoryItem(
                    onClicked = { order ->
                        onOrderClicked?.invoke(
                            order.id
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

@Preview
@Composable
private fun OrderHistorypreview() {
    OrderHistoryScreen(0,OrderHistoryState())
}