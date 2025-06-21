package com.nat.greco.screens.clients

import android.widget.Toast
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.screens.home.presentation.HomeState.Companion.dummyList
import com.nat.greco.screens.home.presentation.components.ListItem
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DarkBlue

@Composable
fun ClientsScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 16.dp),

    ) {
        val context = LocalContext.current

        Spacer(Modifier.height(16.dp))

        Text(
            "العملاء",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
        )
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
                ClientItem(
                    item = item
                )
            }
        }
    }
}

@Preview
@Composable
private fun ClientsScreenP() {
    ClientsScreen()
}