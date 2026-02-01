package com.nat.bless.screens.home.presentation

import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.dialog.AppDialog
import com.nat.bless.base.ui.datePicker.AppDatePicker
import com.nat.bless.base.locationChecker.LocationHandler
import com.nat.bless.base.locationChecker.LocationServiceReceiver
import com.nat.bless.base.permissions.LocationPermissionDialog
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.home.domain.models.Route
import com.nat.bless.screens.home.presentation.components.AddNewOrderAndClientSheetLayout
import com.nat.bless.screens.home.presentation.components.ListItem
import com.nat.bless.screens.home.presentation.components.NewClientSheetLayout
import com.nat.bless.screens.home.presentation.components.NewOrderSheetLayout
import com.nat.bless.screens.home.presentation.components.SearchTapItem
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.NotDeliverRed
import com.nat.bless.utils.convertDateStringToMillis
import com.nat.bless.utils.formattedDateToEnglish
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    events: ((HomeEvents) -> Unit)? = null,
    onClick: ((Route, String) -> Unit)? = null,
    navigateToNotification: (() -> Unit)? = null,
    onDayDetailsClicked: ((String) -> Unit)? = null,
    onDailyReportClicked: ((String) -> Unit)? = null,
    openNewOrderScreen: (() -> Unit)? = null,
    openAddClientScreen: (() -> Unit)? = null,
    signOut: (() -> Unit)? = null
) {
    val context = LocalContext.current

//    var selectedTap by remember {
//        mutableStateOf(CourierSheetTypes.All.name)
//    }
    var showWaybillSortBottomSheet by remember { mutableStateOf(false) }
    var showPickupSortBottomSheet by remember { mutableStateOf(false) }
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var selectedDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }

    var isLocationEnabled by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var addNewClientBottomSheet by remember { mutableStateOf(false) }
    var addNewOrderBottomSheet by remember { mutableStateOf(false) }
    var floatingButtonBottomSheet by remember { mutableStateOf(false) }
    var showWaybillFilterBottomSheet by remember { mutableStateOf(false) }
    var showPickupFilterBottomSheet by remember { mutableStateOf(false) }
    val locationError by remember { mutableStateOf("") }

//    var courierType by remember { mutableStateOf(state.courierType ?: CourierSheetTypes.All.name) }

    var shouldShowDialog by remember { mutableStateOf(false) }
    var isDatePickerOpen by remember { mutableStateOf(false) }
    val locationServiceReceiver = rememberUpdatedState(LocationServiceReceiver(
        context = context,
        isLocationEnabled = { isEnabled ->
            Log.d("##LocationEnabled", "$isEnabled")
            shouldShowDialog = !isEnabled
            isLocationEnabled = isEnabled
        }
    ))
    var showSignoutDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        events?.invoke(HomeEvents.DataChanged(selectedDate.ifEmpty { LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy", java.util.Locale.ENGLISH)) }))
        events?.invoke(HomeEvents.CallRoutes)
    }
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

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
                val name =
                    StringBuilder().append("أهلاً").append(" ").append(state.userName.orEmpty()).toString()

                Text(
                    text = name,
                    style = CompactTypography.labelMedium.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                )


//                IconButton(onClick = {
//                    Toast.makeText(
//                        context,
//                        "This feature will be available soon",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_scan),
//                        contentDescription = null,
//                        tint = DarkBlue
//                    )
//                }
//
//                IconButton(onClick = {
//                    Toast.makeText(
//                        context,
//                        "This feature will be available soon",
//                        Toast.LENGTH_SHORT
//                    )
//                        .show()
//                }) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_message),
//                        contentDescription = null,
//                        tint = DarkBlue
//                    )
//                }
//
//                IconButton(onClick = {
//                    navigateToNotification?.invoke()
//                }) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_notification),
//                        contentDescription = null,
//                        tint = DarkBlue
//                    )
//                }


                SearchTapItem(query = "خروج",
                    isSelected = true,
                    mainColor = NotDeliverRed,
                    onClick = {
                        showSignoutDialog = true
                    }
                )

            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                SearchTapItem(query = "الكل",
//                    isSelected = courierType == CourierSheetTypes.All.name,
//                    onClick = {
//                    })

                Spacer(Modifier.width(8.dp))

//            SearchTapItem(query = "توصيل شحنة",
//                isSelected = courierType == CourierSheetTypes.waybill.name,
//                onClick = {
//                    courierType = CourierSheetTypes.waybill.name
////                    selectedTap = CourierSheetTypes.waybill.name
//                    events?.invoke(HomeEvents.CallWaybillCouriers(CourierSheetTypes.waybill.name))
//                })
//            Spacer(Modifier.width(8.dp))

//            SearchTapItem(query = "طلبية من",
//                isSelected = courierType == CourierSheetTypes.pickup.name,
//                onClick = {
//                    courierType = CourierSheetTypes.pickup.name
////                    selectedTap = CourierSheetTypes.pickup.name
//                    events?.invoke(HomeEvents.CallPickupCouriers(CourierSheetTypes.pickup.name))
//
//                })
//            Spacer(Modifier.width(8.dp))
                Spacer(Modifier.weight(1f))
                SearchTapItem(query = "تقرير اليوم",
                    isSelected = true,
                    onClick = {
                        onDailyReportClicked?.invoke(state.date.orEmpty())
                    }
                )
                Spacer(Modifier.width(12.dp))

                SearchTapItem(query = "انهاء اليوم",
                    isSelected = true,
                    onClick = {
                        onDayDetailsClicked?.invoke(state.date.orEmpty())
                    }
                )

                Spacer(Modifier.width(12.dp))

                SearchTapItem(query = "اختر التاريخ",
                    isSelected = true,
                    onClick = {
                        isDatePickerOpen = true
                    }
                )
//                IconButton(onClick = {
////                    if (courierType == CourierSheetTypes.waybill.name) {
////                        showWaybillSortBottomSheet = true
////                    } else {
////                        showPickupSortBottomSheet = true
////                    }
//                }) {
//                    Image(
//                        painter = painterResource(R.drawable.ic_sort), contentDescription = null
//                    )
//                }
//
//                Spacer(Modifier.width(12.dp))
//
//                IconButton(onClick = {
////                    if (courierType == CourierSheetTypes.waybill.name) {
////                        showWaybillFilterBottomSheet = true
////                    } else {
////                        showPickupFilterBottomSheet = true
////                    }
//                }) {
//                    Image(
//                        painter = painterResource(R.drawable.ic_filter), contentDescription = null
//                    )
//                }
            }

            Spacer(modifier = Modifier.height(12.dp))


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "خط السير", style = CompactTypography.labelMedium.copy(fontSize = 18.sp),
                )
                Spacer(Modifier.width(8.dp))


                val count =
                    StringBuilder().append("( ").append(state.model?.routes?.size ?: 0).append("").append("")
                        .append(" )").toString()

                Text(
                    text = count,
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                )
            }
            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                itemsIndexed(state.model?.routes ?: listOf()){index,item ->
                    val isLast = index == state.model?.routes?.lastIndex
                    ListItem(
                        item = item,
                        modifier = if (isLast) {
                            Modifier.padding(bottom = 24.dp)
                        } else {
                            Modifier
                        },
                        onClick = { model ->
                            onClick?.invoke(model, item.note)
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { floatingButtonBottomSheet = true },
            containerColor = MediumBlue,
            modifier = Modifier.align(Alignment.BottomStart)

        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = White)
        }
    }

    if (isDatePickerOpen) {
        AppDatePicker(
            onDateSelected = {
                isDatePickerOpen = false
                selectedDate = it?.formattedDateToEnglish().orEmpty()

                events?.invoke(HomeEvents.DataChanged(it?.formattedDateToEnglish().orEmpty()))

            },
            initialSelectedDateMillis = selectedDate.convertDateStringToMillis(),
            onDismiss = { isDatePickerOpen = false })
    }



    if (showSignoutDialog) {
        AppDialog(
            dialogMessage = "هل أنت متأكد من تسجيل الخروج؟",
            onDismiss = { showSignoutDialog = false },
            onConfirm = {
                showSignoutDialog = false
                events?.invoke(HomeEvents.clearUser)
                signOut?.invoke()
            },
            confirmButtonTitle = "نعم",
            cancelButtonTitle = "لا"
        )
    }

    if (addNewOrderBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                addNewOrderBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ){
            NewOrderSheetLayout()
        }
    }

    if (addNewClientBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                addNewClientBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ){
            NewClientSheetLayout()
        }
    }

    if (floatingButtonBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                floatingButtonBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            AddNewOrderAndClientSheetLayout(
                onNewOrderClicked = {
                    floatingButtonBottomSheet = false
//                    addNewOrderBottomSheet = true
                    openNewOrderScreen?.invoke()
                },
                onNewClientClicked = {
                    floatingButtonBottomSheet = false
                    openAddClientScreen?.invoke()
                }
            )
        }
    }

    if (showWaybillSortBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showWaybillSortBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
//            SortBottomSheetLayout(onClick = { sort ->
////                if (sort.equals(state.waybillSortType)) {
////                    showWaybillSortBottomSheet = false
////                } else {
////                    events?.invoke(HomeEvents.WaybillSortTypeClicked(sort))
////                    showWaybillSortBottomSheet = false
////                }
//
//
//            }, onResetClick = {
//                events?.invoke(HomeEvents.ResetWaybillSortClicked)
//                showWaybillSortBottomSheet = false
//            }, alreadySelectedSort = state.waybillSortType.orEmpty()
//            )
        }

    }

    if (showPickupSortBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showPickupSortBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
//            SortBottomSheetLayout(onClick = { sort ->
////                if (sort.equals(state.pickupSortType)) {
////                    showPickupSortBottomSheet = false
////                } else {
////                    events?.invoke(HomeEvents.PickupSortTypeClicked(sort))
////                    showPickupSortBottomSheet = false
////                }
//
//
//            }, onResetClick = {
//                events?.invoke(HomeEvents.ResetPickupSortClicked)
//                showPickupSortBottomSheet = false
//            }, alreadySelectedSort = state.pickupSortType.orEmpty()
//            )
        }
    }

    if (showWaybillFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showWaybillFilterBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
//            WaybillFilterBottomSheetLayout(
//                onClick = { filter ->
//                    if (filter.equals(state.waybillFilterType)) {
//                        showWaybillFilterBottomSheet = false
//                    } else {
//                        events?.invoke(HomeEvents.WaybillFilterTypeClicked(filter))
//                        showWaybillFilterBottomSheet = false
//                    }
//
//
//                }, onResetClick = {
//                    events?.invoke(HomeEvents.WaybillResetFilterClicked)
//                    showWaybillFilterBottomSheet = false
//                }, alreadySelectedFilter = state.waybillFilterType.orEmpty()
//            )
        }

    }

    if (showPickupFilterBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showPickupFilterBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
//            PickupFilterBottomSheetLayout(
//                onClick = { filter ->
//                    if (filter.equals(state.pickupFilterType)) {
//                        showPickupFilterBottomSheet = false
//                    } else {
//                        events?.invoke(HomeEvents.PickupFilterTypeClicked(filter))
//                        showPickupFilterBottomSheet = false
//                    }
//
//
//                }, onResetClick = {
//                    events?.invoke(HomeEvents.PickupResetFilterClicked)
//                    showPickupFilterBottomSheet = false
//                }, alreadySelectedFilter = state.pickupFilterType.orEmpty()
//            )
        }
    }

    if (state.errorMessage?.isNotEmpty() == true && (state.errorMessage != "Token is not valid")) {
        ShowToast(state.errorMessage)
    }

    if (state.isLoading) {
        FullLoading()
    }
//    if (state.errorMessage?.isNotEmpty() == true) {
//        ShowToast(state.errorMessage)
//    } else if (locationError.isNotEmpty()) {
//        ShowToast(locationError)
//    }
//
//    // if the user is unauthorized, sign out
    if (state.errorMessage == "Token is not valid") {
        signOut?.invoke()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(locale = "ar")
@Composable
private fun HomeScreenPreview() {
    HomeScreen(HomeState())
}