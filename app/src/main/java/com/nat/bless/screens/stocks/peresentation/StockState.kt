package com.nat.bless.screens.stocks.peresentation

import com.nat.bless.screens.stocks.models.returnsModel.ReturnedListData

data class StockState(
    val isLoading: Boolean = false,
    val error: String = "",
    val stockSearchQuery: String = "",
    val returnsSearchQuery: String = "",
    val returnsList: List<ReturnedListData> = listOf(),
    val stockList: List<com.nat.bless.screens.addNewOrders.models.StockListData> = listOf()
)
