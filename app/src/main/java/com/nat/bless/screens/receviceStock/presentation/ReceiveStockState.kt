package com.nat.bless.screens.receviceStock.presentation

import com.nat.bless.screens.receviceStock.domain.models.ReceiveStockResponse

data class ReceiveStockState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val model: List<ReceiveStockResponse> = listOf(),
    val error: String = "",
    val id: Int = 0
)
