package com.nat.greco.screens.dayDetails.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.greco.R
import com.nat.greco.base.ui.appButton.AppButton
import com.nat.greco.base.ui.appLoading.FullLoading
import com.nat.greco.base.ui.toast.ShowToast
import com.nat.greco.screens.dayDetails.domain.models.Data
import com.nat.greco.screens.home.domain.models.HomeModel
import com.nat.greco.screens.home.presentation.HomeState.Companion.dummyList
import com.nat.greco.ui.theme.CompactTypography
import com.nat.greco.ui.theme.DeliverGreen
import com.nat.greco.ui.theme.Gray
import com.nat.greco.ui.theme.WhiteGray

@Composable
fun DayDetailsScreen(
    date: String,
    state: DayDetailsState,
    events: ((DayDetailsEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null
) {

    LaunchedEffect(Unit) {
        events?.invoke(DayDetailsEvents.DataChanged(date))
    }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "تفاصيل اليوم",
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

            if (state.model?.result?.data?.isEmpty() == true) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "لا يوجد تفاصيل", style = CompactTypography.headlineMedium.copy(
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

            }else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    itemsIndexed(items = state.model?.result?.data ?: listOf()) { index, item ->
                        val isLast = index == state.model?.result?.data?.lastIndex
                        ListItem(
                            item = item,
                            modifier = if (isLast) {
                                Modifier.padding(bottom = 24.dp)
                            } else {
                                Modifier
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.weight(1f))
        }

        AppButton(
            modifier = Modifier
                .fillMaxWidth(.8f)
                .align(Alignment.BottomCenter),
            text = "انهاء اليوم",
            onClick = {
                events?.invoke(DayDetailsEvents.EndData)
            }
        )
    }

//    LaunchedEffect(state.error) {
//        if (state.error.isNotEmpty()) {
//            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
//        }    }

    if (state.isLoading) {
        FullLoading()
    }
}

@Composable
private fun ListItem(
    item: Data? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(.6f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {


                    Text(
                        "اسم التاجر",
                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
                    )

                    Text(
                        text = item?.customer?.name.orEmpty(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }

//                DeliveryStatus(status = "تم")

            }


//            DeliveryStatusBar(status = "تم")

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)

            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(.6f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "اجمالي التحصيل: ",
                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
                    )

                    Text(
                        text = item?.collected_amount.toString() + " EGP",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(.6f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "اجمالي المرتجع: ",
                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
                    )

                    Text(
                        text = item?.returned_qty.toString(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(.6f),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        "اجمالي المخزون: ",
                        style = CompactTypography.labelMedium.copy(color = Gray, fontSize = 12.sp)
                    )

                    Text(
                        text = item?.sold_qty.toString(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                }
            }
        }
    }
}


@Composable
private fun DeliveryStatus(status: String) {
    val color = when (status) {
        "تم" -> DeliverGreen
        "لم تم" -> Gray
        else -> Gray
    }
    Card(
        modifier = Modifier.wrapContentSize(),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(24.dp),

        ) {

        Text(
            status,
            style = CompactTypography.labelMedium.copy(
                color = WhiteGray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()

        )

    }
}

@Composable
private fun DeliveryStatusBar(status: String) {
    val color = when (status) {
        "تم" -> DeliverGreen
        "لم تم" -> Gray
        else -> Gray
    }

    val icon = when (status) {
        "تم" -> painterResource(R.drawable.ic_check_circle)
        "لم تم" -> painterResource(R.drawable.ic_cancel)
        else -> painterResource(R.drawable.ic_filled_circle)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp), contentAlignment = Alignment.Center
    ) {
        HorizontalDivider(
            thickness = 2.dp,
            color = color.copy(alpha = .2f),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filled_circle),
                contentDescription = null,
                tint = color
            )

//            Icon(
//                painter = painterResource(R.drawable.ic_filled_circle),
//                contentDescription = null,
//                tint = color
//            )

            Icon(
                painter = icon,
                contentDescription = null,
                tint = color,
            )
        }
    }

}

@Preview
@Composable
private fun EndDayScreenPreivew() {
    DayDetailsScreen("", DayDetailsState())
}