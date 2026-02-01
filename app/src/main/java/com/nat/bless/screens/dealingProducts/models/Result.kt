package com.nat.bless.screens.dealingProducts.models

data class Result(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)