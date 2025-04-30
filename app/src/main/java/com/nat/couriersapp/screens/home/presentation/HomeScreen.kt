package com.nat.couriersapp.screens.home.presentation

import android.content.IntentFilter
import android.location.LocationManager
import android.util.Log
import android.widget.Toast
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
import com.nat.couriersapp.base.locationChecker.LocationHandler
import com.nat.couriersapp.base.locationChecker.LocationServiceReceiver
import com.nat.couriersapp.base.permissions.LocationPermissionDialog
import com.nat.couriersapp.base.ui.appLoading.FullLoading
import com.nat.couriersapp.base.ui.toast.ShowToast
import com.nat.couriersapp.screens.home.domain.models.CourierSheetTypes
import com.nat.couriersapp.screens.home.domain.models.HomeModel
import com.nat.couriersapp.screens.home.presentation.components.CourierItem
import com.nat.couriersapp.screens.home.presentation.components.PickupFilterBottomSheetLayout
import com.nat.couriersapp.screens.home.presentation.components.WaybillFilterBottomSheetLayout
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
    onClick: ((HomeModel) -> Unit)? = null,
    navigateToNotification: (() -> Unit)? = null,
    signOut: (() -> Unit)? = null
) {
    val context = LocalContext.current

    var selectedTap by remember {
        mutableStateOf(CourierSheetTypes.All.name)
    }
    var showWaybillSortBottomSheet by remember { mutableStateOf(false) }
    var showPickupSortBottomSheet by remember { mutableStateOf(false) }

    var isLocationEnabled by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var showWaybillFilterBottomSheet by remember { mutableStateOf(false) }
    var showPickupFilterBottomSheet by remember { mutableStateOf(false) }
    val locationError by remember { mutableStateOf("") }

    var courierType by remember { mutableStateOf(state.courierType ?: CourierSheetTypes.All.name) }

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
        events?.invoke(HomeEvents.RefreshCouriers)
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

            IconButton(onClick = {
                Toast.makeText(context, "This feature will be available soon", Toast.LENGTH_SHORT)
                    .show()
            }) {
                Icon(
                    painter = painterResource(R.drawable.ic_message),
                    contentDescription = null,
                    tint = DarkBlue
                )
            }

            IconButton(onClick = {
                navigateToNotification?.invoke()
            }) {
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
                isSelected = courierType == CourierSheetTypes.All.name,
                onClick = {
                    courierType = CourierSheetTypes.All.name
//                    selectedTap = CourierSheetTypes.All.name
                    events?.invoke(HomeEvents.CallAllCouriers(CourierSheetTypes.All.name))
                })

            Spacer(Modifier.width(8.dp))

            SearchTapItem(query = "توصيل شحنة",
                isSelected = courierType == CourierSheetTypes.waybill.name,
                onClick = {
                    courierType = CourierSheetTypes.waybill.name
//                    selectedTap = CourierSheetTypes.waybill.name
                    events?.invoke(HomeEvents.CallWaybillCouriers(CourierSheetTypes.waybill.name))
                })
            Spacer(Modifier.width(8.dp))

            SearchTapItem(query = "طلبية من",
                isSelected = courierType == CourierSheetTypes.pickup.name,
                onClick = {
                    courierType = CourierSheetTypes.pickup.name
//                    selectedTap = CourierSheetTypes.pickup.name
                    events?.invoke(HomeEvents.CallPickupCouriers(CourierSheetTypes.pickup.name))

                })
            Spacer(Modifier.width(8.dp))

            IconButton(onClick = {
                if (courierType == CourierSheetTypes.waybill.name) {
                    showWaybillSortBottomSheet = true
                } else {
                    showPickupSortBottomSheet = true
                }
            }) {
                Image(
                    painter = painterResource(R.drawable.ic_sort), contentDescription = null
                )
            }

            IconButton(onClick = {
                if (courierType == CourierSheetTypes.waybill.name) {
                    showWaybillFilterBottomSheet = true
                } else {
                    showPickupFilterBottomSheet = true
                }
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
            val text = if (state.courierType == CourierSheetTypes.waybill.name) "قائمة الشحنات" else "قائمة الطلبيات"

            Text(
                text = text, style = CompactTypography.labelMedium.copy(fontSize = 18.sp),
            )
            Spacer(Modifier.width(8.dp))

            val countText = if (state.courierType == CourierSheetTypes.waybill.name) "شحنة" else "طلبية"

            val count =
                StringBuilder().append("( ").append(state.model?.obj?.total ?: 0).append(" ").append(countText)
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
                    courierType = courierType,
                    onClick = { model ->
                        onClick?.invoke(model.copy(courierType = courierType))
                    }
                )
            }
        }
    }

    if (showWaybillSortBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showWaybillSortBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            SortBottomSheetLayout(onClick = { sort ->
                if (sort.equals(state.waybillSortType)) {
                    showWaybillSortBottomSheet = false
                } else {
                    events?.invoke(HomeEvents.WaybillSortTypeClicked(sort))
                    showWaybillSortBottomSheet = false
                }


            }, onResetClick = {
                events?.invoke(HomeEvents.ResetWaybillSortClicked)
                showWaybillSortBottomSheet = false
            }, alreadySelectedSort = state.waybillSortType.orEmpty()
            )
        }

    }

    if (showPickupSortBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showPickupSortBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            SortBottomSheetLayout(onClick = { sort ->
                if (sort.equals(state.pickupSortType)) {
                    showPickupSortBottomSheet = false
                } else {
                    events?.invoke(HomeEvents.PickupSortTypeClicked(sort))
                    showPickupSortBottomSheet = false
                }


            }, onResetClick = {
                events?.invoke(HomeEvents.ResetPickupSortClicked)
                showPickupSortBottomSheet = false
            }, alreadySelectedSort = state.pickupSortType.orEmpty()
            )
        }
    }

    if (showWaybillFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showWaybillFilterBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            WaybillFilterBottomSheetLayout(
                onClick = { filter ->
                    if (filter.equals(state.waybillFilterType)) {
                        showWaybillFilterBottomSheet = false
                    } else {
                        events?.invoke(HomeEvents.WaybillFilterTypeClicked(filter))
                        showWaybillFilterBottomSheet = false
                    }


                }, onResetClick = {
                    events?.invoke(HomeEvents.WaybillResetFilterClicked)
                    showWaybillFilterBottomSheet = false
                }, alreadySelectedFilter = state.waybillFilterType.orEmpty()
            )
        }

    }

    if (showPickupFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showPickupFilterBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            PickupFilterBottomSheetLayout(
                onClick = { filter ->
                    if (filter.equals(state.pickupFilterType)) {
                        showPickupFilterBottomSheet = false
                    } else {
                        events?.invoke(HomeEvents.PickupFilterTypeClicked(filter))
                        showPickupFilterBottomSheet = false
                    }


                }, onResetClick = {
                    events?.invoke(HomeEvents.PickupResetFilterClicked)
                    showPickupFilterBottomSheet = false
                }, alreadySelectedFilter = state.pickupFilterType.orEmpty()
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

    // if the user is unauthorized, sign out
    if (state.errorCode == 401) {
        signOut?.invoke()
    }
}

@Preview(locale = "ar")
@Composable
private fun HomeScreenPreview() {
    HomeScreen(HomeState())
}