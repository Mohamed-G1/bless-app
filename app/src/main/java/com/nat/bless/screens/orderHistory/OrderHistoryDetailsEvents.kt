package com.nat.bless.screens.orderHistory

sealed class OrderHistoryDetailsEvents {
    data class OrderIdChanged(val id : Int) : OrderHistoryDetailsEvents()
}