package com.nat.couriersapp.screens.home.presentation

import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.couriersapp.R
import com.nat.couriersapp.base.broadCasstReciver.LocationReceiver
import com.nat.couriersapp.base.locationChecker.LocationHandler
import com.nat.couriersapp.base.locationChecker.LocationServiceReceiver
import com.nat.couriersapp.base.permissions.LocationPermissionDialog
import com.nat.couriersapp.base.ui.appLoading.FullLoading
import com.nat.couriersapp.base.ui.shimmer.shimmerLoading
import com.nat.couriersapp.base.ui.toast.ShowToast
import com.nat.couriersapp.screens.home.domain.models.CourierSheetTypes
import com.nat.couriersapp.screens.home.domain.models.HomeModel
import com.nat.couriersapp.screens.home.presentation.components.CourierItem
import com.nat.couriersapp.screens.home.presentation.components.FilterBottomSheetLayout
import com.nat.couriersapp.screens.home.presentation.components.SortBottomSheetLayout
import com.nat.couriersapp.screens.home.presentation.components.SearchTapItem
import com.nat.couriersapp.ui.theme.CompactTypography
import com.nat.couriersapp.ui.theme.DarkBlue
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    events: ((HomeEvents) -> Unit)? = null,
    onClick: ((HomeModel) -> Unit)? = null
) {
    val context = LocalContext.current

    var selectedTap by remember {
        mutableStateOf(CourierSheetTypes.All.name)
    }
    var showSortBottomSheet by remember { mutableStateOf(false) }
    var isLocationEnabled by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var showFilterBottomSheet by remember { mutableStateOf(false) }
    val locationError by remember { mutableStateOf("") }
    var shouldShowDialog by remember { mutableStateOf(false) }

    val locationServiceReceiver = rememberUpdatedState(LocationServiceReceiver(
        context = context,
        isLocationEnabled = { isEnabled ->
            Log.d("##LocationEnabled", "$isEnabled")
            shouldShowDialog = !isEnabled
            isLocationEnabled = isEnabled
        }
    ))

    // Register the receiver
    DisposableEffect(context) {
        try {
            context.registerReceiver(
                locationServiceReceiver.value,
                IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
            )
        } catch (e: Exception) {
            Log.e("##ReceiverError", "Error registering receiver: ${e.message}")
        }

        // Unregister the receiver when the composable is disposed
        onDispose {
            try {
                context.unregisterReceiver(locationServiceReceiver.value)
            } catch (e: IllegalArgumentException) {
                Log.e("##ReceiverError", "Receiver not registered: ${e.message}")
            }
        }
    }


    // this to launch the location observer one the screen is opened
    LaunchedEffect(Unit) {
        val isEnabled = LocationHandler.isLocationServiceEnabled(context)
        shouldShowDialog = isEnabled.not()
        isLocationEnabled = isEnabled
    }

    // Send frequently location each 1 hour
    LaunchedEffect(Unit) {
        if (isLocationEnabled) {
            while (true) {
                val location = LocationHandler.getCurrentLocation(context)
                location?.let {
//                    Log.d(
//                        "##LocationSender",
//                        "Lat: ${it.latitude}, Lon: ${it.longitude}"
//                    )
                    events?.invoke(
                        HomeEvents.SendFrequentlyLocation(
                            lat = it.longitude,
                            long = it.longitude
                        )
                    )
                    delay(3600 * 1000L)
                }
            }
        }
    }



    LaunchedEffect(Unit) {
        events?.invoke(HomeEvents.CallAllCouriers(CourierSheetTypes.waybill.name))
    }

    // Register the LocationReceiver using DisposableEffect (Lifecycle-aware)
//    DisposableEffect(context) {
//        // Create the receiver and lambda to update the ViewModel
//        LocationReceiver.setLocationStateUpdateCallback { isEnabled ->
////            viewModel.updateLocationState(isEnabled)
//            Log.d("HomeScreen", "HomeScreen $isEnabled")
//
//            if (isEnabled.not()) {
//                locationError = "You need to enable location "
//            }
//
//        }
//        // Register the receiver
//        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
//        context.registerReceiver(LocationReceiver, filter)
//
//        // Cleanup the receiver when the composable is disposed
//        onDispose {
//            context.unregisterReceiver(LocationReceiver)
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {

        if (shouldShowDialog) {
            LocationPermissionDialog(
                onDismiss = {
                    shouldShowDialog = false
                }
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val name = StringBuilder().append("أهلاً").append(" ").append(state.userName.orEmpty())
                .append(" !").toString()

            Text(
                text = name,
                style = CompactTypography.labelMedium.copy(fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_message),
                    contentDescription = null,
                    tint = DarkBlue
                )
            }

            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.ic_notification),
                    contentDescription = null,
                    tint = DarkBlue
                )
            }


        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SearchTapItem(query = "الكل",
                isSelected = selectedTap == CourierSheetTypes.All.name,
                onClick = {
                    selectedTap = CourierSheetTypes.All.name
                    events?.invoke(HomeEvents.CallAllCouriers(CourierSheetTypes.waybill.name))
                })

            Spacer(Modifier.width(8.dp))

            SearchTapItem(query = "توصيل طلبية",
                isSelected = selectedTap == CourierSheetTypes.waybill.name,
                onClick = {
                    selectedTap = CourierSheetTypes.waybill.name
                    events?.invoke(HomeEvents.CallAllCouriers(CourierSheetTypes.waybill.name))
                })
            Spacer(Modifier.width(8.dp))

            SearchTapItem(query = "إستلام طلبية",
                isSelected = selectedTap == CourierSheetTypes.pickup.name,
                onClick = {
                    selectedTap = CourierSheetTypes.pickup.name
                    events?.invoke(HomeEvents.CallAllCouriers(CourierSheetTypes.pickup.name))

                })
            Spacer(Modifier.width(8.dp))

            IconButton(onClick = {
                showFilterBottomSheet = true
            }) {
                Image(
                    painter = painterResource(R.drawable.ic_sort), contentDescription = null
                )
            }

            IconButton(onClick = {
                showSortBottomSheet = true
            }) {
                Image(
                    painter = painterResource(R.drawable.ic_filter), contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "قائمة الطرود", style = CompactTypography.labelMedium.copy(fontSize = 18.sp),
            )
            Spacer(Modifier.width(8.dp))

            val count =
                StringBuilder().append("( ").append(state.model?.obj?.total ?: 0).append(" شحنة")
                    .append(" )").toString()

            Text(
                text = count,
                style = CompactTypography.labelMedium.copy(fontSize = 14.sp, color = Color.Gray),
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = state.homeList) { item ->
                CourierItem(
                    item = item,
                    onClick = { model ->
                        onClick?.invoke(model)
                    }
                )
            }
        }
    }

    if (showSortBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSortBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            SortBottomSheetLayout(onClick = { sort ->
                if (sort.equals(state.sortType)) {
                    showSortBottomSheet = false
                } else {
                    events?.invoke(HomeEvents.SortTypeClicked(sort))
                    showSortBottomSheet = false
                }


            }, onResetClick = {
                events?.invoke(HomeEvents.ResetSortClicked)
                showSortBottomSheet = false
            }, alreadySelectedSort = state.sortType.orEmpty()
            )
        }

    }

    if (showFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showFilterBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            FilterBottomSheetLayout(onClick = { filter ->
                if (filter.equals(state.filterType)) {
                    showFilterBottomSheet = false
                } else {
                    events?.invoke(HomeEvents.FilterTypeClicked(filter))
                    showFilterBottomSheet = false
                }


            }, onResetClick = {
                showFilterBottomSheet = false
            }, alreadySelectedFilter = state.filterType.orEmpty()
            )
        }
    }

    if (state.isLoading) {
        FullLoading()
    }
    if (state.errorMessage?.isNotEmpty() == true) {
        ShowToast(state.errorMessage)
    } else if (locationError.isNotEmpty()) {
        ShowToast(locationError)
    }
}

@Preview(locale = "ar")
@Composable
private fun HomeScreenPreview() {
    HomeScreen(HomeState())
}