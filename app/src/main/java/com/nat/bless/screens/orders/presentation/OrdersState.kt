package com.nat.bless.screens.orders.presentation

import com.nat.bless.screens.orders.domain.models.OrdersResponse
import com.nat.bless.screens.orders.domain.models.ReturnsResponse
import java.time.LocalDate

data class OrdersState(
    val isLoading : Boolean = false,
    val model : List<OrdersResponse> = listOf(),
    val returnsModel : List<ReturnsResponse> = listOf(),
    val error : String = "",
    val selectedMonth: Int = LocalDate.now().monthValue,  // 1–12
    val selectedYear: Int  = LocalDate.now().year,
)
