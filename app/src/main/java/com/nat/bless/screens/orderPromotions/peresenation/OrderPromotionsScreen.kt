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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.orderPromotions.domain.models.AllowedProduct
import com.nat.bless.screens.orderPromotions.domain.models.AppliedProducts
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.Orange
import com.nat.bless.ui.theme.WhiteGray
import kotlin.collections.component1
import kotlin.collections.component2

@OptIn(ExperimentalMaterial3Api::class)
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

    var selectedItem by remember { mutableStateOf<AllowedProduct?>(null) }
    var openUnitOfMeasureSheet by remember { mutableStateOf(false) }

    // Map of product_id -> qty for all selected products
    val selectedProducts = remember { mutableStateMapOf<Int, Int>() }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)



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
//                    Text(
//                        " نسبة البونص: ${state.model?.bonus_percentage.toString() + " %"}",
//                        style = CompactTypography.labelMedium.copy(
//                            fontSize = 14.sp,
//                            color = Color.Gray
//                        )
//                    )

                    Text(
                        " مبلغ البونص المتبقي: ${state.model?.remaining_bonus_amount.toString() + " ُEGP"}",
                        style = CompactTypography.labelMedium.copy(
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    )
                }
            }



            Spacer(Modifier.height(16.dp))

            if (state.model?.allowed_products?.isNotEmpty() == true) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Use GridCells.Adaptive(minSize) for adaptive columns
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = 80.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(8.dp) // Optional padding
                ) {
                    items(items = state.model.allowed_products) { item ->
                        val qty = selectedProducts[item.id] ?: 0
                        val isSelected = qty > 0

                        SelectableItem(
                            item = item,
                            isSelected = isSelected,
                            onCheckedChange = { isChecked ->
//                                if (isChecked) {
//                                    selectedItems.add(item)
//                                } else {
//                                    selectedItems.remove(item)
//                                }
                            },
                            onClicked = {
                                selectedItem = item
                                openUnitOfMeasureSheet = true
                            },
                            selectedCount = qty,
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
            enabled = selectedProducts.isNotEmpty(),
            text = "تطبيق العروض",
            onClick = {
                val appliedProducts = selectedProducts.map { (productId, qty) ->
                    AppliedProducts(product_id = productId, qty = qty)
                }
                events?.invoke(OrderPromotionsEvents.ApplyPromotion(appliedProducts))
            },
        )
    }

    // Unit of Measure Bottom Sheet
    if (openUnitOfMeasureSheet && selectedItem != null) {
        UnitOfMeasureBottomSheet(
            sheetState = sheetState,
            item = selectedItem!!,
            currentQty = selectedProducts[selectedItem!!.id] ?: 0,
            onDismiss = {
                openUnitOfMeasureSheet = false
            },
            onConfirm = { qty ->
                if (qty > 0) {
                    selectedProducts[selectedItem!!.id] = qty
                } else {
                    selectedProducts.remove(selectedItem!!.id)
                }
                openUnitOfMeasureSheet = false
            },
            onRemove = { productId ->
                selectedProducts.remove(productId)
                openUnitOfMeasureSheet = false
            }
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
    onCheckedChange: (Boolean) -> Unit,
    onClicked: () -> Unit,
    selectedCount: Int
) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = if (isSelected) MediumBlue.copy(alpha = 0.05f) else White
            ),
            border = BorderStroke(
                width = 2.dp,
                color = if (isSelected) MediumBlue else WhiteGray
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = item.name,
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp)
                )
                Text(
                    text = "السعر: ${item.price}",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray)
                )

                Card(
                    onClick = { onClicked.invoke() },
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) Orange else MediumBlue
                    )
                ) {
                    Icon(
                        imageVector = if (isSelected) Icons.Default.Edit else Icons.Default.Add,
                        modifier = Modifier.padding(6.dp),
                        tint = White,
                        contentDescription = null
                    )
                }
            }
        }

        // Badge showing selected quantity
        if (isSelected && selectedCount > 0) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(6.dp),
                containerColor = Orange
            ) {
                Text(
                    text = "×$selectedCount",
                    style = CompactTypography.headlineLarge.copy(
                        fontSize = 10.sp,
                        color = White
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                )
            }
        }
    }
}


@Preview
@Composable
private fun OrderPromotionsPreview() {
    OrderPromotionsScreen(OrderPromotionsState(), customerId = 0)
}