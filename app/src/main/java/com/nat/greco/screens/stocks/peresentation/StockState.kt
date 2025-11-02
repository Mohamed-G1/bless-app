package com.nat.greco.screens.stocks.peresentation

import com.nat.greco.screens.stocks.models.returnsModel.Data

data class StockState(
    val isLoading: Boolean = false,
    val error: String = "",
    val stockSearchQuery: String = "",
    val returnsSearchQuery: String = "",
    val returnsList: List<Data> = listOf(),
    val stockList: List<com.nat.greco.screens.addNewOrders.models.Data> = listOf()
)
