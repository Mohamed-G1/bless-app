package com.nat.greco.screens.stocks.models.returnsModel

data class Result(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)