package com.nat.greco.screens.orderDetails.presentation

sealed class OrderDetailsEvents {
    data class OrderIdChanged(val orderId: Int) : OrderDetailsEvents()
}