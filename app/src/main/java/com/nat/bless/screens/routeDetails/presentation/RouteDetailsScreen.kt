package com.nat.bless.screens.routeDetails.presentation

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.core.text.HtmlCompat
import com.journeyapps.barcodescanner.ScanContract
import com.nat.bless.R
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.routeDetails.presentation.compoenets.CollectSheetLayout
import com.nat.bless.screens.routeDetails.presentation.compoenets.NotDeliveredBottomSheet
import com.nat.bless.screens.routeDetails.presentation.compoenets.RefusalReasonsBottomSheet
import com.nat.bless.screens.home.domain.models.Route
import com.nat.bless.screens.home.presentation.components.NewOrderSheetLayout
import com.nat.bless.screens.routeDetails.presentation.compoenets.AddNewOrderAndCollectSheetLayout
import com.nat.bless.screens.routeDetails.presentation.compoenets.ConfirmReasonsBottomSheet
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.DeliverGreen
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.NotDeliverRed
import com.nat.bless.ui.theme.WhiteGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteDetailsScreen(
    state: RouteDetailsState,
    events: ((RouteDetailsEvents) -> Unit)? = null,
    route: Route? = null,
    note: String? = null,
    onBackClicked: (() -> Unit)? = null,
    onDealingProductsClicked: ((Int) -> Unit)? = null,
    onLastOrdersClicked: ((Int) -> Unit)? = null,
    onAccountsClicked: ((Int) -> Unit)? = null,
    onPromotionsClicked: ((Int) -> Unit)? = null,
    openNewProductsScreen: ((Int) -> Unit)? = null,
    openPriceListScreen: ((Int) -> Unit)? = null,
    openContractsScreen: ((String) -> Unit)? = null,
    openCollectScreen: ((Int) -> Unit)? = null,
    onCartClicked: ((Int) -> Unit)? = null,

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
    var showNotDeliveredBottomSheet by remember { mutableStateOf(false) }
    var showRefusalReasonsBottomSheet by remember { mutableStateOf(false) }
    var showConfirmReasonsBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )


    LaunchedEffect(Unit) {
        events?.invoke(RouteDetailsEvents.HomeModelChanged(route))
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
            ) {
                Text(
                    "تفاصيل الزيارة",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    onCartClicked?.invoke(
                        state.homeModel?.customer_id?.id ?: 0
                    )
                }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_shopping_cart_24),
                        contentDescription = null
                    )
                }
                Spacer(Modifier.width(12.dp))

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.greko_icon),
                    contentDescription = null,
                    modifier =
                        Modifier

                            .clip(CircleShape)
                            .size(60.dp),
                    contentScale = ContentScale.Crop
                )

                Column(
                    Modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Column(
                            Modifier,
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            if (state.homeModel?.customer_id?.is_active == true) {
                                Text(
                                    "نشط",
                                    style = CompactTypography.labelMedium.copy(
                                        fontSize = 12.sp,
                                        color = DeliverGreen
                                    )
                                )
                            }

//                            Text(
//                                "عميل مميز",
//                                style = CompactTypography.labelMedium.copy(
//                                    fontSize = 12.sp,
//                                    color = Color.Gray
//                                )
//                            )
                            Text(
                                state.homeModel?.customer_id?.create_date.orEmpty(),
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                        VerticalDivider(
                            thickness = 1.dp,
                            color = Color.Gray,
                            modifier = Modifier.height(50.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))


                        Column(
                            Modifier,
                            horizontalAlignment = Alignment.Start,
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {

                            Text(
                                state.homeModel?.customer_id?.note.orEmpty(),
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            )
                            Text(
                                state.homeModel?.customer_id?.tags?.joinToString(",").orEmpty(),
                                style = CompactTypography.labelMedium.copy(
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )
                            )
//                            Text(
//                                "مدير مبيعات: الاسم",
//                                style = CompactTypography.labelMedium.copy(
//                                    fontSize = 12.sp,
//                                    color = Color.Gray
//                                )
//                            )
                        }
                    }
                }
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
                                route?.customer_id?.name.orEmpty(),
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
                            Text(
                                route?.customer_id?.phone.orEmpty(),
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
                                            Uri.parse("tel:${route?.customer_id?.phone.orEmpty()}")
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

                    Text(
                        route?.customer_id?.address.orEmpty(),
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        ),

                        )
                }


            }


            Spacer(Modifier.height(24.dp))

            Text("الحد الائتماني", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
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
                        state.homeModel?.customer_id?.credit_limit?.toString() ?: "",
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    )
                }
            }



            Spacer(Modifier.height(24.dp))

            Text("ملاحظات", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
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
                        note ?: "",
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
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = {
                        val uri =
                            Uri.parse("geo:0,0?q=${route?.customer_id?.address.orEmpty()}")
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
                            Uri.parse("tel:${route?.customer_id?.phone.orEmpty()}")
                        )
                        context.startActivity(intent)

                    }) {
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
                                    Uri.parse("https://wa.me/${route?.customer_id?.phone.orEmpty()}") // WhatsApp URL with phone number
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
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = {
                            onDealingProductsClicked?.invoke(state.homeModel?.customer_id?.id ?: 0)
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
                            onAccountsClicked?.invoke(route?.customer_id?.id ?: 0)
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
                            onLastOrdersClicked?.invoke(state.homeModel?.customer_id?.id ?: 0)
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
            Spacer(Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    IconButton(
                        onClick = {
                            onPromotionsClicked?.invoke(route?.customer_id?.id ?: 0)
                        }) {
                        Image(
                            painter = painterResource(R.drawable.ic_send_message),
                            contentDescription = null
                        )
                    }
                    Text(
                        "العروض الحالية",
                        style = CompactTypography.bodyMedium.copy(fontSize = 12.sp)
                    )


                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    IconButton(
                        onClick = {
                            openContractsScreen?.invoke(
                                htmlToPlainText(
                                    route?.customer_id?.contract ?: ""
                                )
                            )
                        }) {
                        Image(
                            painter = painterResource(R.drawable.ic_send_message),
                            contentDescription = null
                        )
                    }
                    Text(
                        "  التعاقد   ",
                        style = CompactTypography.bodyMedium.copy(fontSize = 12.sp)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    IconButton(
                        onClick = {
                            openPriceListScreen?.invoke(route?.customer_id?.id ?: 0)
                        }) {
                        Image(
                            painter = painterResource(R.drawable.ic_send_message),
                            contentDescription = null
                        )
                    }
                    Text(
                        "قائمة الاسعار",
                        style = CompactTypography.bodyMedium.copy(fontSize = 12.sp)
                    )
                }
            }

            Spacer(Modifier.height(200.dp))

        }


        if (showNotDeliveredBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showNotDeliveredBottomSheet = false
                }, sheetState = sheetState, containerColor = White
            ) {
                NotDeliveredBottomSheet(
                    onClick = {
                        showNotDeliveredBottomSheet = false

                    }
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
                    refusalList = state.confirmedAndCancelledModel?.data?.distinct() ?: listOf(),
                    onClick = { refusalName, refusalId ->
                        if (refusalName.isBlank() && refusalId == 0) {
                            Toast.makeText(
                                context,
                                "You should choose a status",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (state.confirmedAndCancelledModel?.data?.isNotEmpty() == true) {
                                events?.invoke(
                                    RouteDetailsEvents.CancelRouteReasonsChanged(
                                        routeId = state.homeModel?.id ?: 0,
                                        reasonId = refusalId
                                    )
                                )
                                showRefusalReasonsBottomSheet = false


                            }

                        }

                    },
                    alreadySelectedRefusalId = state.refusalId,
                )
            }

        }


        if (showConfirmReasonsBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showConfirmReasonsBottomSheet = false
                }, sheetState = sheetState, containerColor = White
            ) {
                ConfirmReasonsBottomSheet(
                    refusalList = state.confirmedAndCancelledModel?.data?.distinct() ?: listOf(),
                    onClick = { confirmName, confirmId ->
                        if (confirmName.isBlank() && confirmId == 0) {
                            Toast.makeText(
                                context,
                                "You should choose a status",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            if (state.confirmedAndCancelledModel?.data?.isNotEmpty() == true) {
                                events?.invoke(
                                    RouteDetailsEvents.ConfirmRouteReasonsChanged(
                                        routeId = state.homeModel?.id ?: 0,
                                        reasonId = confirmId
                                    )
                                )
                                showConfirmReasonsBottomSheet = false
                            }
                        }

                    },
                    alreadySelectedRefusalId = state.confirmId,
                )
            }

        }


        FloatingActionButton(
            onClick = { floatingButtonBottomSheet = true },
            containerColor = MediumBlue,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(bottom = 120.dp)
                .padding(horizontal = 16.dp)

        ) {
            Icon(Icons.Default.Add, contentDescription = "Add", tint = White)
        }

        Spacer(modifier = Modifier.height(32.dp))

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
//                        showConfirmReasonsBottomSheet = true

                        events?.invoke(
                            RouteDetailsEvents.ConfirmRouteReasonsChanged(
                                routeId = state.homeModel?.id ?: 0,
                                reasonId = 0
                            )
                        )
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))

                AppButton(
                    text = "الغاء الزيارة",
                    buttonColor = NotDeliverRed,
                    boarderColor = NotDeliverRed,
                    modifier = Modifier.weight(.5f),
                    onClick = {
                        showRefusalReasonsBottomSheet = true
                    }
                )

//                Spacer(modifier = Modifier.width(12.dp))
//
//                AppButton(
//                    text = "طلب جديد",
//                    buttonColor = MediumBlue,
//                    boarderColor = MediumBlue,
//                    modifier = Modifier.weight(.5f),
//                    onClick = {
//                    }
//                )


            }
        }
    }

    if (state.isLoading) {
        FullLoading()
    }
    if (state.errorMessage.isNotEmpty()) {
        ShowToast(state.errorMessage)
    }

//    if (state.navigateBack) {
////        onBackClicked?.invoke()
//    }


    if (addNewOrderBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                addNewOrderBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            NewOrderSheetLayout()
        }
    }

    if (collectBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                collectBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            CollectSheetLayout()
        }
    }

    if (floatingButtonBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                floatingButtonBottomSheet = false
            }, sheetState = sheetState, containerColor = White
        ) {
            AddNewOrderAndCollectSheetLayout(
                onNewOrderClicked = {
                    floatingButtonBottomSheet = false
//                    addNewOrderBottomSheet = true
                    openNewProductsScreen?.invoke(state.homeModel?.customer_id?.id ?: 0)
                },
                onCollectClicked = {
                    floatingButtonBottomSheet = false

                    openCollectScreen?.invoke(state.homeModel?.customer_id?.id ?: 0)
                }

            )
        }
    }
}


fun htmlToPlainText(html: String): String {
    return HtmlCompat.fromHtml(html, HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
}

@Preview(showBackground = true, locale = "ar")
@Composable
private fun CourierDetailsPreview() {
    RouteDetailsScreen(RouteDetailsState())
}
