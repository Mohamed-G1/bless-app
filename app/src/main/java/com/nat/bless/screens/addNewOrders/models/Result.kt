package com.nat.bless.screens.addNewOrders.models

data class Result(
    val code: Int,
    val `data`: List<StockListData>,
    val message: String
)