package com.nat.greco.screens.returnsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.imageLoader.AppImageLoading
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.orders.domain.models.OrdersLine
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun ReturnsScreen(
    orderId: Int,
    state: ReturnsState,
    events: ((ReturnsEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    navigateToReturnDetails: (() -> Unit)? = null,

    ) {

    LaunchedEffect(orderId) {
        events?.invoke(ReturnsEvents.OrderIdChanged(id = orderId))
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(vertical = 24.dp, horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "مرتجع",
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "رقم الطلب",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.name.orEmpty(),
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "تاريخ الطلب",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.date_order.orEmpty(),
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }
                }
            }




            Spacer(Modifier.height(16.dp))


//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight(),
//                colors = CardDefaults.cardColors(containerColor = White),
//                border = BorderStroke(1.dp, color = WhiteGray)
//            ) {
//                Column(
//                    modifier = Modifier.padding(16.dp)
//                ) {
//                    Text(
//                        text = "اسم الشركة",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
//                    )
//                    Spacer(Modifier.height(8.dp))
//                    Text(
//                        text = "اسم الطلب",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
//                    )
//
//                    Spacer(Modifier.height(8.dp))
//                    Row(
//                        modifier = Modifier.fillMaxWidth(),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(
//                            text = "1200 EGP",
//                            style = CompactTypography.headlineLarge.copy(
//                                fontSize = 14.sp,
//                                color = Gray
//                            )
//                        )
//
//                        Text(
//                            text = "5 (كرتونة)",
//                            style = CompactTypography.headlineLarge.copy(
//                                fontSize = 14.sp,
//                                color = Gray
//                            )
//                        )
//                    }
//
//                }
//            }
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.model?.order_lines ?: listOf()) { item ->
                    ReturnItem(
                        item = item,
                        onValueChanged = { list ->
                            events?.invoke(
                                ReturnsEvents.CounterChanged(
                                    lines = list
                                )
                            )
                        }
                    )
                }
            }
        }



        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Text(
                "تفاصيل الدفع",
                style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
            )
            Spacer(Modifier.height(16.dp))
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
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "الاجمالي",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.amount_untaxed.toString() + " EGP",
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ضريبة",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.amount_tax.toString() + " EGP",
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }

                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "الاجمالي + الضريبة",
                            style = CompactTypography.headlineLarge.copy(fontSize = 14.sp)
                        )
                        Text(
                            text = state.model?.amount_total.toString() + " EGP",
                            style = CompactTypography.headlineLarge.copy(
                                fontSize = 14.sp,
                                color = Gray
                            )
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            AppButton(
                modifier = Modifier
                    .fillMaxWidth(.8f)
                    .align(Alignment.CenterHorizontally),
                text = "تأكيد المرتجع",
                onClick = {
                    events?.invoke(ReturnsEvents.ReturnProducts)
                }
            )
        }
    }
    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }

    if (state.isLoading) {
        FullLoading()
    }

    if (state.navigateBack){
        onBackClicked?.invoke()
    }
}

@Composable
fun ReturnItem(
    item: OrdersLine,
    onValueChanged: ((List<Lines>) -> Unit)? = null
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
//            Image(
//                painter = painterResource(R.drawable.greco_product),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(84.dp)
//                    .clip(
//                        RoundedCornerShape(12.dp)
//                    ),
//                contentScale = ContentScale.Crop
//            )

            AppImageLoading(
                imgUrl = item.product_id.barcode,
                modifier = Modifier
                    .size(84.dp)
                    .clip(
                        RoundedCornerShape(12.dp)
                    ),
                contentScale = ContentScale.Crop
            )



            Spacer(Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
//                Row {
//                    Text(
//                        "الخميس 20 سبتمبر 2022",
//                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
//                    )
//
//                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "اسم الصنف",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item.product_id.name,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "السعر",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item.price_subtotal.toString() + " EGP",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "الكمية",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )


                    ReturnsCounter(
                        item = item,
                        initValue = 0,
                        onCounterChange = { list ->
                            onValueChanged?.invoke(list)
                        }
                    )
                }
            }
        }

    }

}


@SuppressLint("MutableCollectionMutableState")
@Composable
fun ReturnsCounter(
    item: OrdersLine,
    modifier: Modifier = Modifier,
    isAllowedToBeZero: Boolean = true,
    onCounterChange: (List<Lines>) -> Unit,
    initValue: Int = 0
) {
    var counter by remember {
        mutableIntStateOf(initValue)
    }

    val lines = remember { mutableStateListOf<Lines>() }

    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = modifier.height(30.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                if (counter > 0) {
                    counter--
                    updateLines(lines, item, counter) { newList ->
                        onCounterChange.invoke(newList)
                    }
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_subtract_counter),
                    tint = DarkGray,
                    contentDescription = null
                )
            }


            Text(
                text = "$counter  " + item.product_uom,
                style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
            )

            IconButton(onClick = {
                counter++
                updateLines(lines, item, counter) { newList ->
                    onCounterChange.invoke(newList)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_counter),
                    tint = DarkGray,
                    contentDescription = null
                )
            }
        }
    }
}

private fun updateLines(
    lines: MutableList<Lines>,
    item: OrdersLine,
    counter: Int,
    setLines: (MutableList<Lines>) -> Unit
) {
    val updated = lines.toMutableList() // make a copy

    // Remove any existing entry for the same item.id
    updated.removeAll { it.product_id == item.id }

    // If counter > 0, re-add with updated count
    if (counter > 0) {
        updated.add(Lines(item.product_id.id, counter.toDouble()))
    }

    setLines(updated) // reassign new reference
}


@Preview
@Composable
private fun ReturnsScreenPreview() {
    ReturnsScreen(0, state = ReturnsState())
}