package com.nat.bless.screens.salespersonBonusScreen

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupPositionProvider
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.datePicker.MonthYearPickerDialog
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.WhiteGray
import com.nat.bless.utils.formattedDateFromMillisMonth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SalespersonBonusScreen(
    state: SalespersonBonusState = SalespersonBonusState(),
    events: ((SalespersonBonusEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null

) {
    var selectedDate by rememberSaveable { mutableStateOf("") }
    var isDatePickerOpen by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        events?.invoke(SalespersonBonusEvents.OnMonthSelected(selectedDate.ifEmpty {
            LocalDate.now()
                .format(DateTimeFormatter.ofPattern("MM/yyyy", java.util.Locale.ENGLISH))
        }))
    }

    val density = LocalDensity.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    "سجل المندوب",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )
                Spacer(Modifier.weight(1f))

                TooltipBox(
                    positionProvider = object : PopupPositionProvider {
                        override fun calculatePosition(
                            anchorBounds: IntRect,
                            windowSize: IntSize,
                            layoutDirection: LayoutDirection,
                            popupContentSize: IntSize
                        ): IntOffset {
                            val spacing = with(density) { 8.dp.roundToPx() }

                            return IntOffset(
                                x = anchorBounds.left +
                                        (anchorBounds.width - popupContentSize.width) / 2,
                                y = anchorBounds.bottom + spacing
                            )
                        }
                    },
                    tooltip = {
                        Text(
                            "اختر الشهر",
                            style = CompactTypography.headlineMedium.copy(fontSize = 14.sp)
                        )
                    },
                    state = rememberTooltipState()
                ) {
                    IconButton(
                        onClick = {
                            isDatePickerOpen = true
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_calenda),
                            contentDescription = null
                        )
                    }
                }

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }


            Spacer(Modifier.height(24.dp))
        }
        item {

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
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        " شهر: ${state.month}",
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    )

                    Text(
                        " اجمالي المكافأت: ${state.bonusDetails?.data?.total_bonus ?: 0}",
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    )
                }
            }
            Spacer(Modifier.height(24.dp))

        }

        if (state.bonusDetails?.data?.records == null) {
            item {
                Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Spacer(Modifier.height(500.dp))

                    Text(
                        "لا يوجد سجل لهذا الشهر",
                        style = CompactTypography.headlineMedium.copy(fontSize = 18.sp),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            items(
                items = state.bonusDetails?.data?.records ?: listOf()
            ) { item ->
                BonusItem(item)
            }

        }

//        val text1 = StringBuilder()
//            .append("زيارات إيجابية: ")
//            .append(0)
//            .toString()
//        Text(
//            text1,
//            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
//        )
//        Spacer(Modifier.height(12.dp))
//
//        val text2 = StringBuilder()
//            .append("زيارات ملغاة: ")
//            .append(0)
//            .toString()
//        Text(
//            text2,
//            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
//        )
//
//        Spacer(Modifier.height(12.dp))
//
//        val text3 = StringBuilder()
//            .append("إجمالي الزيارات: ")
//            .append(0)
//            .toString()
//        Text(
//            text3,
//            style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
//        )
    }

    if (isDatePickerOpen) {
        MonthYearPickerDialog(
            initialYear = 2026,
            initialMonth = 1,
            onConfirm = { year, month ->
                val ym = java.time.YearMonth.of(year, month)
                val millis = ym.atDay(1)
                    .atStartOfDay(java.time.ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()


                selectedDate = millis.formattedDateFromMillisMonth()
                events?.invoke(
                    SalespersonBonusEvents.OnMonthSelected(
                        millis.formattedDateFromMillisMonth()
                    )
                )
                isDatePickerOpen = false
            },
            onDismiss = { isDatePickerOpen = false },
            forceArabic = true
        )


    }
    if (state.error?.isNotEmpty() == true) {
        ShowToast(state.error)
    }
//
    if (state.isLoading) {
        FullLoading()
    }
}

@Composable
fun BonusItem(
    item: Record,
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(2.dp, WhiteGray)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row {
                    Text(
                        item.date,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )

                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "النوع",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item.type,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "العميل",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = item.customer,
                        style = CompactTypography.headlineLarge.copy(
                            fontSize = 12.sp,
                            color = Gray,
                            textAlign = TextAlign.End
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = " كود الطلب",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item.sale_order.toString(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "الكمية",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item.amount.toString(),
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "ملاحظات",
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                    )
                    Text(
                        text = item.note,
                        style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                    )
                }


            }
        }

    }

}


@Preview
@Composable
private fun DeliveryReporterScreenPerview() {
    SalespersonBonusScreen()
}