package com.nat.bless.screens.salespersonScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.login.domain.models.Detail
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun SalespersonTargetScreen(
    state: SalespersonState = SalespersonState(),
    onBackClicked: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "تارجت المندوب",
                style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
            )

            IconButton(onClick = { onBackClicked?.invoke() }) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = null
                )
            }
        }


        Spacer(Modifier.height(24.dp))

        Text(
            state.name,
            style = CompactTypography.labelMedium.copy(
                fontSize = 18.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(24.dp))

        Text("تفاصيل التارجت", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
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
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "المستوى: ${state.model?.target_level}",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )

                Text(
                    " الهدف الشهري: ${state.model?.monthly_target}",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )

                Text(
                    " المستهلك: ${state.model?.consumed}",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )

                Text(
                    " المتبقي: ${state.model?.remaining}",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )

                Text(
                    " نسبة الإنجاز: ${state.model?.achievement_rate}%",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            }
        }


        Spacer(Modifier.height(24.dp))
        Text("ملخص المكافأة", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
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
                    "اجمالي الشهر: ${state.model?.monthly_target}",
                    style = CompactTypography.labelMedium.copy(
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                )
            }
        }

//        Spacer(Modifier.height(24.dp))
//        if (state.details.isNotEmpty()){
//            Text("تفاصيل المكافأة", style = CompactTypography.labelMedium.copy(fontSize = 16.sp))
//            Spacer(Modifier.height(16.dp))
//        }
//        state.details.forEach { detail ->
//            BonusItem(detail)
//        }


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


    if (state.error.isNotEmpty()) {
        ShowToast(state.error)
    }
//
    if (state.isLoading) {
        FullLoading()
    }
}

@Composable
fun BonusItem(item: Detail) {
    Surface(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp)),
        color = White
    ) {

        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                item.type,
                style = CompactTypography.labelMedium.copy(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            Text(
                "العدد: ${item.count}",
                style = CompactTypography.labelMedium.copy(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            Text(
                "الاجمالي: ${item.total}",
                style = CompactTypography.labelMedium.copy(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )
        }
    }

}


@Preview
@Composable
private fun SalespersonTargetScreenPerview() {
    SalespersonTargetScreen()
}