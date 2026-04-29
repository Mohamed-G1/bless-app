package com.nat.bless.screens.orderPromotions.peresenation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.orderPromotions.domain.models.AllowedProduct
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun OrderPromotionsScreen(
    state: OrderPromotionsState,
    events: ((OrderPromotionsEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    customerId: Int
) {

    LaunchedEffect(customerId) {
        events?.invoke(OrderPromotionsEvents.GetOrderPromotions(customerId))
    }

    val selectedItems = remember { mutableStateListOf<AllowedProduct>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(top = 16.dp),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "العروض",
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

            if (state.model?.allowed_products?.isNotEmpty() == true) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // 2 columns, change as needed
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items = state.model.allowed_products) { item ->
                        SelectableItem(
                            item = item,
                            isSelected = selectedItems.contains(item),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedItems.add(item)
                                } else {
                                    selectedItems.remove(item)
                                }
                            }
                        )
                    }
                }
            } else {
                Text(
                    "لا يوجد عروض", style = CompactTypography.headlineMedium.copy(
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

        }

        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            enabled = selectedItems.isNotEmpty(),
            text = "تطبيق العروض",
            onClick = {
                events?.invoke(OrderPromotionsEvents.ApplyPromotion(selectedItems))
            },
        )
    }


    if (state.errorMessage.isNotEmpty()) {
        ShowToast(state.errorMessage)
    }

    if (state.isLoading) {
        FullLoading()
    }

    if (state.canNavigateBack) {
        LaunchedEffect(Unit) {
            onBackClicked?.invoke()
        }
    }
}


@Composable
fun SelectableItem(
    item: AllowedProduct,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { onCheckedChange(!isSelected) },
        colors = CardDefaults.cardColors(
            containerColor = White
        ),
        border = BorderStroke(
            2.dp, if (isSelected)
                MediumBlue
            else
                WhiteGray
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Checkbox(
                modifier = Modifier.align(Alignment.Start),
                checked = isSelected,
                onCheckedChange = onCheckedChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = MediumBlue,
                    uncheckedColor = Gray.copy(alpha = 0.3f),
                    checkmarkColor = White
                )
            )

            Text(text = item.name, style = CompactTypography.headlineLarge.copy(fontSize = 12.sp))
            Spacer(Modifier.height(8.dp))
            Text(
                text = " السعر: ${item.price}",
                style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
            )
        }

    }
}


@Preview
@Composable
private fun OrderPromotionsPreview() {
    OrderPromotionsScreen(OrderPromotionsState(), customerId = 0)
}