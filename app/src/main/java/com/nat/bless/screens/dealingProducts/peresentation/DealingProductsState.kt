package com.nat.bless.screens.dealingProducts.peresentation

import com.nat.bless.screens.dealingProducts.models.Data

data class DealingProductsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val customerID: Int? = null,
    val data: List<Data> = listOf()
)