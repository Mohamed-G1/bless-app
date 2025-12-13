package com.nat.greco.screens.receviceStock.presentation

import com.nat.greco.screens.receviceStock.domain.models.ReceiveStockResponse

data class ReceiveStockState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val model: List<ReceiveStockResponse> = listOf(),
    val error: String = "",
    val id: Int = 0
)
