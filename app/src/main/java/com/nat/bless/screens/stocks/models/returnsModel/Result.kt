package com.nat.bless.screens.stocks.models.returnsModel

data class Result(
    val code: Int,
    val `data`: List<ReturnedListData>,
    val message: String
)