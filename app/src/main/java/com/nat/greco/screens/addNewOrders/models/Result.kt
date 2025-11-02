package com.nat.greco.screens.addNewOrders.models

data class Result(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)