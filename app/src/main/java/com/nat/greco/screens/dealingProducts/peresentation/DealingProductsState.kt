package com.nat.greco.screens.dealingProducts.peresentation

import com.nat.greco.screens.dealingProducts.models.Data
import com.nat.greco.screens.dealingProducts.models.DealingProductsResponse

data class DealingProductsState(
    val isLoading: Boolean = false,
    val error: String = "",
    val customerID: Int? = null,
    val data: List<Data> = listOf()
)