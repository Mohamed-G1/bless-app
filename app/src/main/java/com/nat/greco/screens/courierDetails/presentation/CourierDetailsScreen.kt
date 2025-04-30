package com.nat.greco.screens.courierDetails.presentation

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.nat.greco.R
import com.nat.greco.base.locationChecker.LocationHandler
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.courierDetails.presentation.compoenets.DeliveredBottomSheet
import com.nat.greco.screens.courierDetails.presentation.compoenets.NotDeliveredBottomSheet
import com.nat.greco.screens.courierDetails.presentation.compoenets.RefusalReasonsBottomSheet
import com.nat.greco.screens.home.domain.models.CourierSheetTypes
import com.nat.greco.screens.home.domain.models.HomeModel
import com.nat.greco.base.qrCode.QrCodeScreen
import com.nat.greco.screens.courierDetails.presentation.compoenets.CollectSheetLayout
import com.nat.greco.screens.courierDetails.presentation.compoenets.DetailsSheetLayout
import com.nat.greco.screens.home.presentation.components.NewClientSheetLayout
import com.nat.greco.screens.home.presentation.components.NewOrderSheetLayout
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DeliverGreen
import com.nat.greco.ui.theme.MediumBlue
import com.nat.greco.ui.theme.NotDeliverRed
import com.nat.greco.ui.theme.WhiteGray
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourierDetailsScreen(
    state: CourierDetailsState,
    events: ((CourierDetailsEvents) -> Unit)? = null,
    courierModel: HomeModel? = null,
    onBackClicked: (() -> Unit)? = null,
    onProductsListClicked: (() -> Unit)? = null,
    onLastOrdersClicked: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var collectBottomSheet by remember { mutableStateOf(false) }
    var addNewOrderBottomSheet by remember { mutableStateOf(false) }
    var floatingButtonBottomSheet by remember { mutableStateOf(false) }
    val scanLauncher = rememberLauncherForActivityResult(
        contract = ScanContract(),
        onResult = { result ->
            Log.d("BarcodeTest", "Scanned: ${result.contents}")
        }
    )


    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasCamPermission = granted
        }
    )
    LaunchedEffect(key1 = true) {
        launcher.launch(android.Manifest.permission.CAMERA)
    }
    var showDeliveredBottomSheet by remember { mutableStateOf(false) }
    var showNotDeliveredBottomSheet by remember { mutableStateOf(false) }
    var showRefusalReasonsBottomSheet by remember { mutableStateOf(false) }
    var isLocationEnabled by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    var showCodeScannerScreen by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        events?.invoke(CourierDetailsEvents.HomeModelChanged(courierModel))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 24.dp, horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "تفاصيل الزيارة",
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

            Column(
                Modifier
                    .align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.splash_bg),
                    contentDescription = null,
                    modifier =
                    Modifier

                        .align(Alignment.CenterHorizontally)
                        .clip(CircleShape)
                        .size(80.dp),
                    contentScale = ContentScale.Crop
                )

                Text(
                    "Active",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 12.sp,
                        color = DeliverGreen
                    )
                )
                Text(
                    "عميل مميز",
                    style = CompactTypography.labelMedium.copy(fontSize = 12.sp, color = Color.Gray)
                )
                Text(
                    "12/1/2024",
                    style = CompactTypography.labelMedium.copy(fontSize = 12.sp, color = Color.Gray)
                )


            }




            Spacer(modifier = Modifier.height(24.dp))


            Text("بيانات العميل", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = White),
                border = BorderStroke(1.dp, color = WhiteGray)
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Column(
                            modifier = Modifier.weight(1.2f)
                        ) {
                            Text(
                                "العميل",
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                ),
                            )

                            Spacer(Modifier.height(4.dp))

                            Text(
                                courierModel?.consigneeName.orEmpty(),
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.fillMaxWidth(1f)

                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        VerticalDivider(
                            thickness = 1.dp,
                            color = WhiteGray,
                            modifier = Modifier.height(40.dp)
                        )

                        Spacer(Modifier.width(12.dp))

                        Image(
                            painter = painterResource(R.drawable.ic_call),
                            contentDescription = null
                        )

                        Spacer(Modifier.width(8.dp))
                        Column(
                            modifier = Modifier.weight(1f)

                        ) {
                            Text(
                                "هاتف العميل",
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            )
                            Spacer(Modifier.height(8.dp))
                            val text =
                                if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) courierModel?.consigneePhone.orEmpty() else courierModel?.shipperPhone.orEmpty()
                            Text(
                                text,
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    textDecoration = TextDecoration.Underline,
                                    color = Color.Blue,
                                ),
                                maxLines = 1,
                                modifier = Modifier
                                    .fillMaxWidth(.9f)
                                    .clickable {
                                        val intent = Intent(
                                            Intent.ACTION_DIAL,
                                            Uri.parse("tel:${courierModel?.consigneePhone}")
                                        )
                                        context.startActivity(intent)
                                    }

                            )
                        }

                    }

                    Spacer(Modifier.height(12.dp))
                    HorizontalDivider(thickness = 1.dp, color = WhiteGray)

                    Spacer(Modifier.height(12.dp))

                    Text(
                        "العنوان بالتفصيل",
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    )

                    Spacer(Modifier.height(4.dp))

                    val text =
                        if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) courierModel?.consigneeDestinationAddress.orEmpty() else courierModel?.shipperOriginAddress.orEmpty()

                    Text(
                        text,
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        ),

                        )
                }


            }


            Spacer(Modifier.height(24.dp))

            Text("ملاحظات من العميل", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = White),
                border = BorderStroke(1.dp, color = WhiteGray)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        courierModel?.waybillComment.orEmpty(),
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth().padding(horizontal = 10.dp)
                    ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = {
                        val address =
                            if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) courierModel?.consigneeDestinationAddress else courierModel?.shipperOriginAddress

                        val uri =
                            Uri.parse("geo:0,0?q=${address}")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.setPackage("com.google.android.apps.maps") // Explicitly use Google Maps app
                        context.startActivity(intent)

                    }) {
                        Image(
                            painter = painterResource(R.drawable.ic_location),
                            contentDescription = null
                        )
                    }
                    Text("الموقع", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    IconButton(onClick = {
                        val intent = Intent(
                            Intent.ACTION_DIAL,
                            Uri.parse("tel:${courierModel?.consigneePhone}")
                        )
                        context.startActivity(intent)

                    }){
                        Image(
                            painter = painterResource(R.drawable.ic_call),
                            contentDescription = null
                        )
                    }

                    Text("مكالمة", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))

                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = {
                        try {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data =
                                    Uri.parse("https://wa.me/${courierModel?.consigneePhone}") // WhatsApp URL with phone number
                            }
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            // Handle exception if WhatsApp is not installed or other issues
                            Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }) {
                        Image(
                            painter = painterResource(R.drawable.ic_whats),
                            contentDescription = null
                        )
                    }
                    Text("واتساب", style = CompactTypography.bodyMedium.copy(fontSize = 12.sp))
                }


            }


            Spacer(Modifier.height(16.dp))
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                  ,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            onProductsListClicked?.invoke()
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_bar_code),
                            contentDescription = null
                        )
                    }
                    Text(
                        "اصناف التعامل",
                        style = CompactTypography.bodyMedium.copy(fontSize = 12.sp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    IconButton(
                        onClick = {
                        }) {
                        Image(
                            painter = painterResource(R.drawable.ic_send_message),
                            contentDescription = null
                        )
                    }
                    Text(
                        "الحسابات",
                        style = CompactTypography.bodyMedium.copy(fontSize = 12.sp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            onLastOrdersClicked?.invoke()
                        }) {
                        Image(
                            painter = painterResource(R.drawable.ic_send_message),
                            contentDescription = null
                        )
                    }
                    Text(
                        "الطلبات السابقة",
                        style = CompactTypography.bodyMedium.copy(fontSize = 12.sp)
                    )
                }

            }


            Spacer(Modifier.height(24.dp))
            val text =
                if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) "بيانات الشحنة" else " بيانات الطلبية"

//            Text(text, style = CompactTypography.labelMedium.copy(fontSize = 16.sp))

//            Spacer(Modifier.height(16.dp))


//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight(),
//                colors = CardDefaults.cardColors(containerColor = White),
//                border = BorderStroke(1.dp, color = WhiteGray)
//            ) {
//                Box(
//                    modifier = Modifier.fillMaxWidth(),
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxWidth(.6f)
//                            .padding(16.dp)
//                            .padding(vertical = 12.dp)
//                    ) {
//                        val text =
//                            if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) "رقم الشحنة" else " رقم الطلبية"
//                        Text(
//                            text,
//                            style = CompactTypography.labelMedium.copy(
//                                fontSize = 12.sp,
//                                color = Color.Gray
//                            )
//                        )
//
//                        Spacer(Modifier.height(4.dp))
//                        Text(
//                            courierModel?.waybillSerial.toString().orEmpty(),
//                            style = CompactTypography.labelMedium.copy(
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.Bold
//                            ),
//                            modifier = Modifier.fillMaxWidth(1f)
//
//                        )
//
//
//                        Spacer(Modifier.height(12.dp))
//
//                        Text(
//                            "وقت التسليم المتوقع",
//                            style = CompactTypography.labelMedium.copy(
//                                fontSize = 12.sp,
//                                color = Color.Gray
//                            )
//                        )
//
//                        Spacer(Modifier.height(4.dp))
//                        Text(
//                            courierModel?.lastStatusUpdatedDate.toString().orEmpty(),
//                            style = CompactTypography.labelMedium.copy(
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.Bold
//                            ),
//                            modifier = Modifier.fillMaxWidth(1f)
//
//                        )
//
//                        Spacer(Modifier.height(12.dp))
//
//                        Text(
//                            "مبلغ التحصيل",
//                            style = CompactTypography.labelMedium.copy(
//                                fontSize = 12.sp,
//                                color = Color.Gray
//                            )
//                        )
//
//                        Spacer(Modifier.height(4.dp))
//                        val amount = StringBuilder()
//                            .append(courierModel?.collectCharges.toString())
//                            .append(" ")
//                            .append(stringResource(R.string.egp))
//                            .toString()
//                        Text(
//                            amount,
//                            style = CompactTypography.labelMedium.copy(
//                                fontSize = 13.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = Orange
//                            ),
//                            modifier = Modifier.fillMaxWidth(1f)
//
//                        )
//
//                    }
//                    Image(
//                        painter = painterResource(R.drawable.ic_box), contentDescription = null,
//                        modifier = Modifier.align(Alignment.BottomEnd)
//                    )
//                }
//            }
        }

        if (showDeliveredBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showDeliveredBottomSheet = false
                }, sheetState = sheetState, containerColor = White
            ) {
                DeliveredBottomSheet(
                    value = state.clientName,
                    clientName = { name ->
                        events?.invoke(CourierDetailsEvents.ClientNameChanged(name))
                    },
                    clientSignature = { signature ->
                        events?.invoke(CourierDetailsEvents.ClientSignatureChanged(signature))

                    },
                    onDismiss = {
                        scope.launch {
                            val location = LocationHandler.getCurrentLocation(context)
                            if (location != null && LocationHandler.isLocationServiceEnabled(context)) {
                                events?.invoke(
                                    CourierDetailsEvents.LocationChanged(
                                        lat = location.latitude.toString(),
                                        lng = location.longitude.toString()
                                    )
                                )

                                events?.invoke(CourierDetailsEvents.TriggerWaybillDeliveredApi)

                                showDeliveredBottomSheet = false

                            } else {
                                Toast.makeText(
                                    context,
                                    "You need to enable location",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                )
            }
        }

        if (showNotDeliveredBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showNotDeliveredBottomSheet = false
                }, sheetState = sheetState, containerColor = White
            ) {
                NotDeliveredBottomSheet(
                    statusList = state.statusNotDelivered.distinct(),
                    onClick = { statusName, stautsId ->
                        Log.e("NotDeleiveredStatus", statusName + stautsId)
                        if (statusName.isBlank() && stautsId == 0) {
                            Toast.makeText(
                                context,
                                "You should choose a status",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            events?.invoke(
                                CourierDetailsEvents.StatusChanged(
                                    name = statusName,
                                    id = stautsId
                                )
                            )

                            showNotDeliveredBottomSheet = false
                            showRefusalReasonsBottomSheet = true
                        }

                    },
                )
            }
        }


        if (showRefusalReasonsBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showRefusalReasonsBottomSheet = false
                }, sheetState = sheetState, containerColor = White
            ) {
                RefusalReasonsBottomSheet(
                    refusalList = state.refusalStatusNotDelivered.distinct(),
                    onClick = { refusalName, refusalId, comment ->
//                        Log.e("NotDeleiveredStatus", statusName + stautsId)
                        if (refusalName.isBlank() && refusalId == 0) {
                            Toast.makeText(
                                context,
                                "You should choose a status",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {

                            if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) {
                                scope.launch {
                                    val location = LocationHandler.getCurrentLocation(context)
                                    if (location != null && LocationHandler.isLocationServiceEnabled(
                                            context
                                        )
                                    ) {
                                        events?.invoke(
                                            CourierDetailsEvents.RefusalWaybillReasonsChanged(
                                                name = refusalName,
                                                id = refusalId,
                                                comments = comment,
                                                lat = location.latitude.toString(),
                                                lng = location.longitude.toString()
                                            )
                                        )
                                        showRefusalReasonsBottomSheet = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "You need to enable location",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }


                            } else {
                                scope.launch {
                                    val location = LocationHandler.getCurrentLocation(context)
                                    if (location != null && LocationHandler.isLocationServiceEnabled(
                                            context
                                        )
                                    ) {
                                        events?.invoke(
                                            CourierDetailsEvents.RefusalPickupReasonsChanged(
                                                name = refusalName,
                                                id = refusalId,
                                                comments = comment,
                                                lat = location.latitude.toString(),
                                                lng = location.longitude.toString()
                                            )
                                        )
                                        showRefusalReasonsBottomSheet = false

                                        showRefusalReasonsBottomSheet = false
                                    } else {
                                        Toast.makeText(
                                            context,
                                            "You need to enable location",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }


                        }

                    },
                    alreadySelectedRefusalId = state.refusalId,
                )
            }

        }


        FloatingActionButton(
            onClick = { floatingButtonBottomSheet = true },
            containerColor = MediumBlue,
            modifier = Modifier.align(Alignment.BottomStart).padding(bottom = 90.dp).padding(horizontal = 16.dp)

        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = White)
        }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                color = White
            ) {
                // status buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    AppButton(
                        text = "تمت الزيارة",
                        buttonColor = DeliverGreen,
                        boarderColor = DeliverGreen,
                        modifier = Modifier.weight(.5f),
                        onClick = {
                        }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    AppButton(
                        text = "الغاء الزيارة",
                        buttonColor = NotDeliverRed,
                        boarderColor = NotDeliverRed,
                        modifier = Modifier.weight(.5f),
                        onClick = {
                        }
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    AppButton(
                        text = "طلب جديد",
                        buttonColor = MediumBlue,
                        boarderColor = MediumBlue,
                        modifier = Modifier.weight(.5f),
                        onClick = {
                        }
                    )



                }
            }
        }

    if (state.isLoading) {
        FullLoading()
    }
    if (state.errorMessage.isNotEmpty()) {
        ShowToast(state.errorMessage)
    }

    if (state.navigateBack) {
        onBackClicked?.invoke()
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

    if (collectBottomSheet){
        ModalBottomSheet(
            onDismissRequest = {
                collectBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ){
            CollectSheetLayout()
        }
    }

     if (floatingButtonBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                floatingButtonBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            DetailsSheetLayout(
                onNewOrderClicked = {
                    floatingButtonBottomSheet = false
                    addNewOrderBottomSheet = true
                },
                onCollectClicked = {
                    floatingButtonBottomSheet = false
                    collectBottomSheet = true
                }
            )
        }
    }



    if (showCodeScannerScreen) {
        if (state.homeModel?.courierType == CourierSheetTypes.waybill.name) {
            QrCodeScreen { value ->
                if (value == state.homeModel.waybillSerial.toString()) {
                    events?.invoke(CourierDetailsEvents.BarCodeScannerValue(value.toLong()))
                    showCodeScannerScreen = false
                } else {
                    Toast.makeText(context, "Please scan the correct serial", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        } else {
            QrCodeScreen { value ->
                if (value == state.homeModel?.waybillSerial.toString()) {
                    events?.invoke(CourierDetailsEvents.PickupBarCodeScannerValue(value.toLong()))
                    showCodeScannerScreen = false
                } else {
                    Toast.makeText(context, "Please scan the correct serial", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}

@Preview(showBackground = true, locale = "ar")
@Composable
private fun CourierDetailsPreview() {
    CourierDetailsScreen(CourierDetailsState())
}
