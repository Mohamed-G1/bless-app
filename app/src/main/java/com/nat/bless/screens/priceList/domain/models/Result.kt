package com.nat.bless.screens.priceList.domain.models

data class Result(
    val code: Int,
    val `data`: List<Data>,
    val message: String
)