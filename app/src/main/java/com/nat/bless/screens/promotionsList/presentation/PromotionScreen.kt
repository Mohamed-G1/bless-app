package com.nat.bless.screens.promotionsList.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.screens.promotionsList.domain.models.PromotionResponse
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun PromotionScreen(
    customerId: Int,
    state: PromotionState,
    events: ((PromotionEvents) -> Unit)? = null,

    onBackClicked: (() -> Unit)? = null
) {

    LaunchedEffect(customerId) {
        events?.invoke(PromotionEvents.CustomerIdChanged(customerId))
    }

    Column(
        modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)
    ) {

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "العروض الحالية",
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

            if (state.model.isEmpty()){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        "لا يوجد عروض حالية", style = CompactTypography.headlineMedium.copy(
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.model) { item ->
                        PromotionItem(
                            item
                        )
                    }
                }
            }

        }
    }
//    if (state.error.isNotEmpty()) {
//        ShowToast(state.error)
//    }

    if (state.isLoading) {
        FullLoading()
    }
}

@Composable
fun PromotionItem(
    item : PromotionResponse
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
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                val text = StringBuilder()
                    .append(" من ")
                    .append(item.date_from)
                    .append(" ")
                    .append("الي ")
                    .append(item.date_to)
                    .toString()
                Text(
                    text = text ,
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                )
                Text(
                    text = item.name,
                    style = CompactTypography.headlineLarge.copy(fontSize = 13.sp)
                )

                Text(
                    text = item.description,
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                )


            }
        }

    }

}
