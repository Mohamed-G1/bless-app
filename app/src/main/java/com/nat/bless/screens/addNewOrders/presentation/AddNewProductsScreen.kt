package com.nat.bless.screens.addNewOrders.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.R
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.imageLoader.AppImageLoading
import com.nat.bless.base.ui.textField.AppTextField
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.screens.UnitOfMeasureSheet
import com.nat.bless.screens.addNewOrders.models.StockListData
import com.nat.bless.screens.addNewOrders.models.SelectedUnit

import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.Gray
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.MediumGray
import com.nat.bless.ui.theme.Orange
import com.nat.bless.ui.theme.WhiteGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewProductsScreen(
    state: NewProductsState,
    events: ((NewProductsEvents) -> Unit)? = null,
    onBackClicked: (() -> Unit)? = null,
    navigateToEditableConfirmOrder: ((Int) -> Unit)? = null,
    navigateToProductDetails: (() -> Unit)? = null,
    customerId: Int
) {

    val context = LocalContext.current
    var openUnitOfMeasueSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    var selectedProduct by remember { mutableStateOf<StockListData?>(null) }
//    val selectedUnits = remember { mutableStateListOf<SelectedUnit>() }

    val SelectedUnitListSaver = listSaver<SnapshotStateList<SelectedUnit>, List<Any>>(
        save = { list ->
            list.map { unit -> listOf(unit.id, unit.uomId, unit.quantity, unit.lot_id, unit.lot_name,unit.productId) }
        },
        restore = { saved ->
            mutableStateListOf<SelectedUnit>().apply {
                addAll(saved.map { arr ->
                    SelectedUnit(
                        id = arr[0] as Int,
                        uomId = arr[1] as Int,
                        quantity = arr[2] as Int,
                        uomName = "",
                        price = 0.0,
                        lot_id = arr[3] as? Int ?: 0,
                        lot_name = arr[4] as? String ?: "",
                        productId = arr[5] as? Int ?: 0
                    )
                })
            }
        }
    )
    val selectedUnits = rememberSaveable(customerId, saver = SelectedUnitListSaver) {
        mutableStateListOf<SelectedUnit>()
    }


    val selectedProducts by remember(selectedUnits) {
        derivedStateOf {
            selectedUnits
                .groupBy { it.id }
                .map { (productId, units) -> productId to units.sumOf { it.quantity } }
        }
    }
    val selectedMap = remember { mutableStateMapOf<Int, Int>() }





    LaunchedEffect(Unit) {
        events?.invoke(NewProductsEvents.CustomerIdChanged(customerId))
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = 24.dp,
                horizontal = 16.dp
            )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    bottom = 24.dp
                )
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "المنتجات",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp)
                )

                IconButton(onClick = { onBackClicked?.invoke() }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_back),
                        contentDescription = null
                    )
                }
            }
//            Spacer(modifier = Modifier.height(8.dp))

//            SearchField()

            Spacer(modifier = Modifier.height(16.dp))
            val productList = state.model?.result?.data ?: listOf()

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

                items(productList) { item ->
//                    val isSelected = selectedMap[item.id]?.let { it > 0 } ?: false
                    val count = selectedProducts.find { it.first == item.id }?.second ?: 0
                    val isSelected = count > 0
                    Log.d("Ids", "${item.id}")

                    ProductItem(
                        isSelected = isSelected,
                        selectedCount = count,
                        data = item,
                        onClicked = {
                            selectedProduct = item
                            openUnitOfMeasueSheet = true
                        },
                    )
                }
            }

        }


        AppButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            text = "اضافة الي العربة",
            onClick = {
                if (selectedUnits.isEmpty())
                    Toast.makeText(context, "يرجي اختيار منتج", Toast.LENGTH_SHORT).show()
                else
                    events?.invoke(NewProductsEvents.AddToCart(selectedUnits))
            },
        )
    }

    if (openUnitOfMeasueSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                openUnitOfMeasueSheet = false
                selectedProduct = null
            }, sheetState = sheetState, containerColor = White
        ) {
            UnitOfMeasureSheet(
                data = selectedProduct,
                onAddClicked = { newUnits ->
                    // merge changes back into shared state
                    mergeSelectedUnits(selectedUnits, selectedMap, newUnits)
                    openUnitOfMeasueSheet = false
                    selectedProduct = null

                    Log.d("Unit", "Received units: $newUnits")
                },  // 👈 pass current selections
                selectedUnits = selectedUnits
            )
        }
    }

    if (state.errorMessage.isNotEmpty() == true) {
        ShowToast(state.errorMessage)
        events?.invoke(NewProductsEvents.ClearMessage)
    }

    if (state.isLoading) {
        FullLoading()
    }

    if (state.navigateToConfirmOrder) {
        navigateToEditableConfirmOrder?.invoke( state.customerId ?: 0)
        events?.invoke(NewProductsEvents.NavigationCompleted)
    }
}

fun mergeSelectedUnits(
    globalList: SnapshotStateList<SelectedUnit>,
    selectedMap: SnapshotStateMap<Int, Int>,
    newList: List<SelectedUnit>
) {
    newList.forEach { unit ->
        val existingIndex = globalList.indexOfFirst {
            it.id == unit.id && it.uomId == unit.uomId
        }
        if (existingIndex != -1) {
            globalList[existingIndex] = unit
        } else {
            globalList.add(unit)
        }

        // update per-product quantity
        val totalQty = globalList
            .filter { it.id == unit.id }
            .sumOf { it.quantity }

        selectedMap[unit.id] = totalQty
    }
}


@Composable
private fun ProductItem(
    data: StockListData? = null,
    onClicked: (() -> Unit)? = null,
    isSelected: Boolean,
    selectedCount: Int
) {
    Log.d("isSelected", "AddNewProductsScreen: $isSelected")

    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(containerColor = White),
            border = BorderStroke(2.dp, WhiteGray)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                AppImageLoading(
                    imgUrl = data?.image_url.orEmpty(),
                    modifier = Modifier
                        .size(100.dp)
                        .clip(
                            RoundedCornerShape(12.dp)
                        ),
                    contentScale = ContentScale.Fit
                )
//            Image(
//                painter = painterResource(R.drawable.splash_bg),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(84.dp)
//                    .clip(
//                        RoundedCornerShape(12.dp)
//                    ),
//                contentScale = ContentScale.Crop
//            )

                Text(
                    text = data?.name.orEmpty(),
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )

                Text(
                    text = "${data?.quantity.toString()} " + "الكمية المتاحة ",
                    style = CompactTypography.headlineLarge.copy(fontSize = 12.sp, color = Gray),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )


                Card(
                    onClick = {
                        onClicked?.invoke()
                    },
                    colors = CardDefaults.cardColors(containerColor = MediumBlue)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        modifier = Modifier.padding(6.dp),
                        tint = White,
                        contentDescription = null
                    )
                }
            }
        }


        if (isSelected && selectedCount > 0) {
            Badge(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                containerColor = Orange
            ) {
                Text(
                    "Selected",
                    style = CompactTypography.headlineLarge
                        .copy(fontSize = 10.sp, color = White),
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
private fun SearchField(

) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = "",
        onValueChange = {
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        trailingCompose = {
            Icon(imageVector = Icons.Default.Search, tint = MediumGray, contentDescription = "")
        }
    )
}


@Composable
fun Counter(
    modifier: Modifier = Modifier,
    isAllowedToBeZero: Boolean = true,
    onCounterChange: (Int) -> Unit,
    value: Int
) {

    Card(
        colors = CardDefaults.cardColors(containerColor = White),
        modifier = modifier.height(50.dp)
    )
    {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            IconButton(onClick = {
                val newValue = value - 1
                if (isAllowedToBeZero) {
                    if (newValue >= 0) onCounterChange(newValue)
                } else {
                    if (newValue > 0) onCounterChange(newValue)
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_subtract_counter),
                    tint = DarkGray,
                    contentDescription = null
                )
            }


            Text(text = value.toString())

            IconButton(onClick = {
                val newValue = value + 1
                onCounterChange(newValue)
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

@Preview(showBackground = true)
@Composable
private fun ProductsPreview() {
    AddNewProductsScreen(NewProductsState(), customerId = 0)
}