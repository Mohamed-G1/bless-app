package com.nat.bless.screens.priceList.presentation

import com.nat.bless.screens.priceList.domain.models.PriceListResponse

data class PriceListState(
    val isLoading: Boolean = false,
    val error: String = "",
    val priceListModel: PriceListResponse? = null,
    val customerId : Int = 0
)
