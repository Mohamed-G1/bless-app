package com.nat.greco.screens.orderHistory

sealed class OrderHistoryDetailsEvents {
    data class OrderIdChanged(val id : Int) : OrderHistoryDetailsEvents()
}