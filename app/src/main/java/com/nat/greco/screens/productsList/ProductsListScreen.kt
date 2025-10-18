package com.nat.greco.screens.productsList

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.screens.home.presentation.HomeState.Companion.dummyList
import com.nat.greco.ui.theme.CompactTypography

@Composable
fun ProductsListScreen(    onBackClicked: (() -> Unit)? = null,
) {
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
                "اصناف التعامل",
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Use GridCells.Adaptive(minSize) for adaptive columns
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(8.dp) // Optional padding
        ) {
            items(dummyList) { item ->
                ProductListItem(
                    onClicked = {
                        // onOrderClicked?.invoke()
                    },
                    item = item
                )
            }
        }
    }
}

@Preview
@Composable
private fun ProductsListScreenP() {
    ProductsListScreen()
}