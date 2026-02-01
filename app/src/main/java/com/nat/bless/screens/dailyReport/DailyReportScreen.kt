package com.nat.bless.screens.dailyReport

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.screens.dayDetails.domain.models.CustomerDetail
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun DailyReportScreen(
    date: String,
    state: DailyReportState,
    events: ((DailyReportEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null
) {
    LaunchedEffect(Unit) {
        events?.invoke(DailyReportEvents.DataChanged(date))
    }

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
                    "التقرير اليومي",
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

            if (state.model?.result?.data?.customer_details?. isEmpty() == true) {
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

            }else{
                Column {
                    val text1 = StringBuilder()
                        .append("زيارات تمت: ")
                        .append(state.model?.result?.data?.viste_count)
                        .toString()
                    Text(
                        text1,
                        style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                    )
                    Spacer(Modifier.height(8.dp))

                    val text2 = StringBuilder()
                        .append("زيارات ملغاة: ")
                        .append(state.model?.result?.data?.not_viste_count)
                        .toString()
                    Text(
                        text2,
                        style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                    )
                    Spacer(Modifier.height(12.dp))
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(items = state.model?.result?.data?.customer_details ?: listOf()) { index, item ->
                            val isLast = index == state.model?.result?.data?.customer_details?.lastIndex
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
            }
        }

    }

//    LaunchedEffect(state.error) {
//        if (state.error.isNotEmpty()) {
//            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
//        }
//    }

    if (state.isLoading) {
        FullLoading()
    }
}

@Composable
private fun ListItem(
    item: CustomerDetail? = null,
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


@Preview
@Composable
private fun DailyReportScreenPreview() {
    DailyReportScreen( date = "2023-10-10", DailyReportState(),)
}