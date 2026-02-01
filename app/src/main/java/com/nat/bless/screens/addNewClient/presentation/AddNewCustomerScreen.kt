package com.nat.bless.screens.addNewClient.presentation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.PopupProperties
import com.nat.bless.R
import com.nat.bless.base.locationChecker.LocationHandler
import com.nat.bless.base.locationChecker.LocationPermissionsChecker
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.textField.AppTextField
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.addNewClient.domain.models.AreasResponse
import com.nat.bless.screens.addNewClient.domain.models.CountryResponse
import com.nat.bless.screens.addNewClient.domain.models.StatesResponse
import com.nat.bless.screens.addNewClient.domain.models.TagsList
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.DarkGray
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.MediumGray
import com.nat.bless.ui.theme.NotDeliverRed
import com.nat.bless.ui.theme.WhiteGray
import kotlinx.coroutines.launch
import kotlin.collections.forEach

@Composable
fun AddNewCustomerScreen(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    openMapScreen: (() -> Unit)? = null,
    name: String? = null,
    lat: String? = null,
    long: String? = null,
) {
//    var isSingleSelected by remember { mutableStateOf(false) }
//    var isMultiSelected by remember { mutableStateOf(false) }

//    LaunchedEffect(name, lat, long) {
//        if (lat!= null&& long != null && name!= null) {
//            events?.invoke(AddNewCustomerEvents.LocationChanged(lat, long, name))
//        }
//    }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val activity = LocalContext.current as Activity
    var isFetchingLocation by remember { mutableStateOf(false) }
    var isLocationGranted by remember {
        mutableStateOf(LocationPermissionsChecker.isLocationPermissionGranted(activity))
    }
    // --- NEW: Fetch location when permission is granted ---
    LaunchedEffect(isLocationGranted) {
        if (isLocationGranted && LocationHandler.isLocationServiceEnabled(context)) {
            isFetchingLocation = true
            // Assuming LocationHandler can be updated to use a callback or Flow
            // For now, we'll keep the coroutine structure
            val location = LocationHandler.getCurrentLocation(context)
            if (location != null) {
                val name = try {
                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                        val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        addresses?.firstOrNull()
                    }?.let { addr ->
                        // prefer a full address line, fallback to locality/subAdminArea
                        addr.getAddressLine(0) ?: addr.locality ?: addr.subAdminArea ?: ""
                    } ?: ""
                } catch (_: Exception) {
                    ""
                }
                // NEW: Store location in the ViewModel state
                events?.invoke(AddNewCustomerEvents.LocationChanged(
                    lat = location.latitude.toString(),
                    long = location.longitude.toString(),
                    name = name
                ))
            }
            isFetchingLocation = false
        }
    }
    val permissionsToRequest = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
    )
    val multiplePermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionsToRequest.forEach { permission ->
                val isGranted = perms[permission] == true
//                viewModel.onPermissionResult(
//                    permission = permission,
//                    isGranted = isGranted
//                )

                isLocationGranted =
                    perms[android.Manifest.permission.ACCESS_FINE_LOCATION] == true || perms[android.Manifest.permission.ACCESS_COARSE_LOCATION] == true

            }
        }
    )

    LaunchedEffect(Unit) {
        if (LocationPermissionsChecker.isLocationPermissionGranted(context = activity).not()) {
            multiplePermissionResultLauncher.launch(permissionsToRequest)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)

    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "عميل جديد",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))


            Text(
                "البيانات الرئيسية",
                style = CompactTypography.headlineMedium.copy(fontSize = 16.sp)
            )
            Spacer(Modifier.height(8.dp))

            ClientName(
                state = state,
                events = events
            )
            Spacer(Modifier.height(8.dp))

            Phone(
                state = state,
                events = events
            )
            Spacer(Modifier.height(8.dp))

            Email(
                state = state,
                events = events
            )
            Spacer(Modifier.height(8.dp))


            ChooseTagsDropDownMenu(
                tags = state.tags?.data ?: listOf(),
                onTagSelected = { tagId ->
                    events?.invoke(AddNewCustomerEvents.TagsChanged(tagId))
                }
            )

            if (!state.isValidTag) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.tagValidationMessage,
                    style = CompactTypography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = NotDeliverRed
                    )
                )
            }

            Spacer(Modifier.height(8.dp))

            ChooseCountryDropDownMenu(
                countries = state.countries,
                onCountrySelected = { countryId ->
                    events?.invoke(AddNewCustomerEvents.CountryChanged(countryId))
                }
            )

            if (!state.isValidCountry) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.countryValidationMessage,
                    style = CompactTypography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = NotDeliverRed
                    )
                )
            }

            Spacer(Modifier.height(8.dp))

            ChooseStatesDropDownMenu(
                states = state.states,
                onStateSelected = { stateId ->
                    events?.invoke(AddNewCustomerEvents.StateChanged(stateId))
                }
            )

            if (!state.isValidState) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.stateValidationMessage,
                    style = CompactTypography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = NotDeliverRed
                    )
                )
            }

            Spacer(Modifier.height(8.dp))



            ChooseCitiesDropDownMenu(
                cities = state.cities,
                onCitySelected = { cityId ->
                    events?.invoke(AddNewCustomerEvents.CityChanged(cityId))
                }
            )



            if (!state.isValidCity) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.cityValidationMessage,
                    style = CompactTypography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = NotDeliverRed
                    )
                )
            }

            Spacer(Modifier.height(8.dp))



            ChooseAreaDropDownMenu(
                areas = state.areas,
                onAreaSelected = { cityId ->
                    events?.invoke(AddNewCustomerEvents.AreaChanged(cityId))
                }
            )



            if (!state.isValidArea) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.areaValidationMessage,
                    style = CompactTypography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = NotDeliverRed
                    )
                )
            }
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LocationField(
                    state = state
//
                )

                IconButton(
                    onClick = {
                        if (!isLocationGranted) {
                            multiplePermissionResultLauncher.launch(permissionsToRequest)
                            return@IconButton
                        }
                        if (!LocationHandler.isLocationServiceEnabled(context)) {
                            return@IconButton
                        }
                        isFetchingLocation = true
                        scope.launch {
                            val location = LocationHandler.getCurrentLocation(context)
                            if (location != null) {
                                val name = try {
                                    kotlinx.coroutines.withContext(kotlinx.coroutines.Dispatchers.IO) {
                                        val geocoder = android.location.Geocoder(context, java.util.Locale.getDefault())
                                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                                        addresses?.firstOrNull()
                                    }?.let { addr ->
                                        addr.getAddressLine(0) ?: addr.locality ?: addr.subAdminArea ?: ""
                                    } ?: ""
                                } catch (_: Exception) {
                                    ""
                                }
                                events?.invoke(
                                    AddNewCustomerEvents.LocationChanged(
                                        lat = location.latitude.toString(),
                                        long = location.longitude.toString(),
                                        name = name
                                    )
                                )
                            }
                            isFetchingLocation = false
                        }
                    }
                ) {
                    Text(
                        text = "تحديث",
                        style = CompactTypography.labelMedium.copy(color = MediumBlue)
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            if (isFetchingLocation) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp,
                        color = Color.Gray
                    )
                    Spacer(Modifier.size(8.dp))
                    Text(
                        text = "جار الحصول علي بيانات الموقع",
                        color = Color.Gray,
                        style = CompactTypography.bodyMedium
                    )
                }
            }


            if (!state.isValidLocation) {
                Spacer(Modifier.height(8.dp))
                Text(
                    text = state.locationValidationMessage,
                    style = CompactTypography.headlineMedium.copy(
                        fontSize = 12.sp,
                        color = NotDeliverRed
                    )
                )
            }

            Spacer(Modifier.height(8.dp))
            Address(
                state = state,
                events = events
            )

            Spacer(Modifier.height(8.dp))

            DistrictiveMark(
                state = state,
                events = events
            )

            Spacer(Modifier.height(24.dp))

            AppButton(
                enabled = !isFetchingLocation,
                text = "اضافة",
                onClick = {
                    events?.invoke(AddNewCustomerEvents.Submit)
                }
            )
        }



        Spacer(Modifier.height(10.dp))

    }

    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }

    if (state.navigateBack) {
        onBackClicked?.invoke()
    }
}


@Composable
private fun LocationField(
    state: AddNewCustomerState,
    onClick: (() -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(.8f),
        placeholder = "الموقع",
        label = "الموقع",
        isError = !state.isValidLocation,
        errorMessage = if (state.isValidLocation) null else state.locationValidationMessage,
        value = state.location,
        onValueChange = {},
        enabled = false,
        borderUnFocusColor = MediumGray,
        borderFocusColor = MediumGray
    )
}


@Composable
private fun ChooseTagsDropDownMenu(
    tags: List<TagsList>,
    onTagSelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }
    var selectedId by remember { mutableIntStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }


    Card(
        onClick = {
            expanded = !expanded
        },
        border = BorderStroke(1.dp, WhiteGray),
        colors = CardDefaults.cardColors(containerColor = WhiteGray),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    fieldSize = coordinates.size.toSize()

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedName.ifBlank { "اختر نوع العميل" },
                style = CompactTypography.labelMedium.copy(
                    color = if (selectedName.isBlank()) Color.Gray else Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                modifier = Modifier
                    .background(White)
                    .padding(2.dp)
                    .width(with(LocalDensity.current) { fieldSize.width.toDp() })
            ) {
                tags.forEach { country ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = country.name,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                style = CompactTypography.labelMedium.copy(
                                    color = Black,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp
                                )
                            )
                        },
                        onClick = {
                            selectedName = country.name
                            selectedId = country.id
                            expanded = false
                            onTagSelected?.invoke(country.id)
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = DarkGray
            )

        }

    }
}



@Composable
private fun ChooseCountryDropDownMenu(
    countries: List<CountryResponse>,
    onCountrySelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }
    var selectedId by remember { mutableIntStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }


    Card(
        onClick = {
            expanded = !expanded
        },
        border = BorderStroke(1.dp, WhiteGray),
        colors = CardDefaults.cardColors(containerColor = WhiteGray),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    fieldSize = coordinates.size.toSize()

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedName.ifBlank { "اختر البلد" },
                style = CompactTypography.labelMedium.copy(
                    color = if (selectedName.isBlank()) Color.Gray else Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                modifier = Modifier
                    .background(White)
                    .padding(2.dp)
                    .width(with(LocalDensity.current) { fieldSize.width.toDp() })
            ) {
                countries.forEach { country ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = country.name,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                style = CompactTypography.labelMedium.copy(
                                    color = Black,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp
                                )
                            )
                        },
                        onClick = {
                            selectedName = country.name
                            selectedId = country.id
                            expanded = false
                            onCountrySelected?.invoke(country.id)
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = DarkGray
            )

        }

    }
}

@Composable
private fun ChooseStatesDropDownMenu(
    states: List<StatesResponse>,
    onStateSelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }
    var selectedId by remember { mutableIntStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }


    Card(
        onClick = {
            expanded = !expanded
        },
        border = BorderStroke(1.dp, WhiteGray),
        colors = CardDefaults.cardColors(containerColor = WhiteGray),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    fieldSize = coordinates.size.toSize()

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedName.ifBlank { "اختر المحافظة" },
                style = CompactTypography.labelMedium.copy(
                    color = if (selectedName.isBlank()) Color.Gray else Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                modifier = Modifier
                    .background(White)
                    .border(1.dp, MediumGray)
                    .padding(2.dp)
                    .width(with(LocalDensity.current) { fieldSize.width.toDp() })
            ) {
                states.forEach { country ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = country.name,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                style = CompactTypography.labelMedium.copy(
                                    color = Black,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp
                                )
                            )
                        },
                        onClick = {
                            selectedName = country.name
                            selectedId = country.id
                            expanded = false
                            onStateSelected?.invoke(country.id)
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = DarkGray
            )

        }

    }
}

@Composable
private fun ChooseCitiesDropDownMenu(
    cities: List<StatesResponse>,
    onCitySelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }
    var selectedId by remember { mutableIntStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }


    Card(
        onClick = {
            expanded = !expanded
        },
        border = BorderStroke(1.dp, WhiteGray),
        colors = CardDefaults.cardColors(containerColor = WhiteGray),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    fieldSize = coordinates.size.toSize()

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedName.ifBlank { "اختر المدينة" },
                style = CompactTypography.labelMedium.copy(
                    color = if (selectedName.isBlank()) Color.Gray else Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                modifier = Modifier
                    .background(White)
                    .padding(2.dp)
                    .width(with(LocalDensity.current) { fieldSize.width.toDp() })
            ) {
                cities.forEach { country ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = country.name,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                style = CompactTypography.labelMedium.copy(
                                    color = Black,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp
                                )
                            )
                        },
                        onClick = {
                            selectedName = country.name
                            selectedId = country.id
                            expanded = false
                            onCitySelected?.invoke(country.id)
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = DarkGray
            )

        }

    }
}

@Composable
private fun ChooseAreaDropDownMenu(
    areas: List<AreasResponse>,
    onAreaSelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedName by remember { mutableStateOf("") }
    var selectedId by remember { mutableIntStateOf(0) }
    var fieldSize by remember { mutableStateOf(Size.Zero) }


    Card(
        onClick = {
            expanded = !expanded
        },
        border = BorderStroke(1.dp, WhiteGray),
        colors = CardDefaults.cardColors(containerColor = WhiteGray),
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()

    )
    {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp, end = 12.dp)
                .onGloballyPositioned { coordinates ->
                    // This value is used to assign to
                    // the DropDown the same width
                    fieldSize = coordinates.size.toSize()

                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = selectedName.ifBlank { "اختر منطقة" },
                style = CompactTypography.labelMedium.copy(
                    color = if (selectedName.isBlank()) Color.Gray else Black,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f, fill = false)
            )


            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                properties = PopupProperties(
                    focusable = false,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true,
                ),
                modifier = Modifier
                    .background(White)
                    .padding(2.dp)
                    .width(with(LocalDensity.current) { fieldSize.width.toDp() })
            ) {
                areas.forEach { country ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = country.name,
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 1,
                                style = CompactTypography.labelMedium.copy(
                                    color = Black,
                                    textAlign = TextAlign.End,
                                    fontSize = 12.sp
                                )
                            )
                        },
                        onClick = {
                            selectedName = country.name
                            selectedId = country.id
                            expanded = false
                            onAreaSelected?.invoke(country.id)
                        }
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = DarkGray
            )

        }

    }
}


@Composable
private fun ClientName(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل اسم العميل",
        label = "ادخل اسم العميل",
        isError = !state.isValidName,
        errorMessage = if (state.isValidName) null else state.nameValidationMessage,
        value = state.name,
        onValueChange = {
            events?.invoke(AddNewCustomerEvents.NameChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}


@Composable
private fun Phone(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل هاتف",
        label = "ادخل هاتف",
        isError = !state.isValidPhone,
        errorMessage = if (state.isValidPhone) null else state.phoneValidationMessage,
        value = state.phone,
        onValueChange = {
            events?.invoke(AddNewCustomerEvents.PhoneChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun Email(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل بريد الكتروني",
        label = "ادخل بريد الكتروني",
        isError = !state.isValidEmail,
        errorMessage = if (state.isValidEmail) null else state.emailValidationMessage,
        value = state.email,
        onValueChange = {
            events?.invoke(AddNewCustomerEvents.EmailChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun Contract(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "معلومات التعاقد",
        label = "معلومات التعاقد",
        isError = !state.isValidContract,
        errorMessage = if (state.isValidContract) null else state.contractValidationMessage,
        value = state.contract,
        onValueChange = {
            events?.invoke(AddNewCustomerEvents.ContractChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun DistrictiveMark(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل علامة مميزة",
        label = "ادخل علامة مميزة",
        maxLines = 2,
        isError = !state.isDistinctiveValid,
        errorMessage = if (state.isDistinctiveValid) null else state.distinctiveValidationMessage,
        value = state.distinctive,
        onValueChange = {
            events?.invoke(AddNewCustomerEvents.DistinctiveChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}

@Composable
private fun Address(
    state: AddNewCustomerState,
    events: ((AddNewCustomerEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "ادخل العنوان",
        label = "ادخل العنوان",
        maxLines = 2,
        isError = !state.isValidAddress,
        errorMessage = if (state.isValidAddress) null else state.addressValidationMessage,
        value = state.address,
        onValueChange = {
            events?.invoke(AddNewCustomerEvents.AddressChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        borderUnFocusColor = MediumGray
    )
}



@Composable
fun ChooseBranches(
    inSingleSelected: Boolean,
    isMutliSelected: Boolean,
    onSingleSelected: () -> Unit,
    onMutliSelected: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Column {
            Text(
                "فرع واحد",
                style = CompactTypography.headlineMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            RadioButton(
                selected = inSingleSelected,
                onClick = { onSingleSelected.invoke() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MediumBlue
                )
            )
        }
        Column {
            Text(
                "اكثر من فرع",
                style = CompactTypography.headlineMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
            RadioButton(
                selected = isMutliSelected,
                onClick = { onMutliSelected.invoke() },
                colors = RadioButtonDefaults.colors(
                    selectedColor = MediumBlue
                )
            )
        }

    }
}



@Preview(showBackground = true)
@Composable
private fun AddNewClientScreenPreview() {
    AddNewCustomerScreen(
        state = AddNewCustomerState(),
        name = "args.name",
        lat = "0.0",
        long = "0.0"
    )
}

@Preview(showBackground = true)
@Composable
private fun ChooseBra() {
    ChooseBranches(false, false, {}) { }
}