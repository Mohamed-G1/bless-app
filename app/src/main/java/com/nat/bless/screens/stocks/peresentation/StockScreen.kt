package com.nat.bless.screens.stocks.peresentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nat.bless.base.ui.appButton.AppButton
import com.nat.bless.base.ui.appLoading.FullLoading
import com.nat.bless.base.ui.textField.AppTextField
import com.nat.bless.base.ui.toast.ShowToast
import com.nat.bless.ui.theme.CompactTypography
import com.nat.bless.ui.theme.MediumBlue
import com.nat.bless.ui.theme.MediumGray
import com.nat.bless.ui.theme.WhiteGray

@Composable
fun StockScreen(
    state: StockState,
    events: ((StockEvents) -> Unit)? = null,
    onReceiveClicked: (() -> Unit)? = null,
) {
    var tabIndex by remember { mutableIntStateOf(1) }
    val tabs = listOf("المرتجع", "المخزون")

    LaunchedEffect(Unit) {
        events?.invoke(StockEvents.GetStockList)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp).safeContentPadding(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    "المخزون الحالي",
                    style = CompactTypography.headlineMedium.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            item {
                TabRow(
                    selectedTabIndex = tabIndex,
                    containerColor = WhiteGray,
                    indicator = {},
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp)),
                    divider = {}
                ) {
                    tabs.forEachIndexed { index, title ->
                        val selected = tabIndex == index
                        val textColor = if (selected) WhiteGray else Color.Black
                        Tab(
                            text = {
                                Text(
                                    text = title,
                                    style = CompactTypography.headlineLarge.copy(
                                        fontSize = 12.sp,
                                        color = textColor
                                    )
                                )
                            },
                            selected = tabIndex == index,
                            onClick = {
                                if (index == 0) {
                                    events?.invoke(StockEvents.GetReturnsStock)
                                } else {
                                    events?.invoke(StockEvents.GetStockList)
                                }

                                tabIndex = index

                            },
                            modifier = Modifier.background(
                                if (selected) MediumBlue else WhiteGray,
                                shape = RoundedCornerShape(12.dp)
                            )
                        )
                    }
                }
            }

            item {
                when (tabIndex) {
                    0 -> {
                        ReturnsSearchField(
                            state = state,
                            events = events,
                        )

                        if (state.returnsList.isEmpty() == true) {
                                Column(
                                    modifier = Modifier.fillMaxSize().padding(top = 80.dp),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        "لا يوجد مرتجع", style = CompactTypography.headlineMedium.copy(
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }


                        }else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .heightIn(max = 1000.dp), // prevent infinite height inside LazyColumn
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(state.returnsList) { item ->
                                    ReturnItem(
                                        onClicked = {

                                        },
                                        item = item
                                    )
                                }
                            }
                        }
                    }

                    1 -> {
                        StockSearchField(
                            state = state,
                            events = events,
                        )

                        if (state.stockList.isEmpty() == true) {
                            Column(
                                modifier = Modifier.fillMaxSize().padding(top = 80.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "لا يوجد مخزون", style = CompactTypography.headlineMedium.copy(
                                        fontSize = 22.sp,
                                        textAlign = TextAlign.Center,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }


                        }else {
                            LazyVerticalGrid(
                                columns = GridCells.Fixed(2),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .heightIn(max = 1000.dp), // prevent infinite height inside LazyColumn
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(8.dp)
                            ) {
                                items(state.stockList) { item ->
                                    StockItem(
                                        onClicked = { },
                                        item = item
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (tabIndex == 1){
            AppButton(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                text = "استلام مخزون",
                onClick = { onReceiveClicked?.invoke() }
            )
        }

    }

    if (state.error.isNotEmpty()){
        ShowToast(state.error)
        events?.invoke(StockEvents.ClearMessage)
    }
    if (state.isLoading) {
        FullLoading()
    }
}


@Composable
private fun StockSearchField(
    state: StockState,
    events: ((StockEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = state.stockSearchQuery,
        onValueChange = {
            events?.invoke(StockEvents.SearchStockQueryChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                if (state.stockSearchQuery.isNotBlank())
                    events?.invoke(StockEvents.TriggerSearchStock)
            }
        ),
        trailingCompose = {
            if (state.stockSearchQuery.isEmpty()) {
                Icon(imageVector = Icons.Default.Search, tint = MediumGray, contentDescription = "")
            } else {
                IconButton(
                    onClick = {
                        events?.invoke(StockEvents.ClearStockSearchQuery)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MediumGray,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

@Composable
private fun ReturnsSearchField(
    state: StockState,
    events: ((StockEvents) -> Unit)? = null,
) {
    AppTextField(
        modifier = Modifier.fillMaxWidth(),
        placeholder = "بحث",
        label = "بحث",
        isError = false,
        errorMessage = null,
        value = state.returnsSearchQuery,
        onValueChange = {
            events?.invoke(StockEvents.SearchReturnsQueryChanged(it))
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions(
            onGo = {
                if (state.returnsSearchQuery.isNotBlank())
                    events?.invoke(StockEvents.TriggerSearchReturns)
            }
        ),
        trailingCompose = {
            if (state.returnsSearchQuery.isEmpty()) {
                Icon(imageVector = Icons.Default.Search, tint = MediumGray, contentDescription = "")
            } else {
                IconButton(
                    onClick = {
                        events?.invoke(StockEvents.ClearReturnsSearchQuery)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        tint = MediumGray,
                        contentDescription = ""
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun StockPreview() {
    StockScreen(StockState())
}