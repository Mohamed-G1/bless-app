package com.nat.bless.screens.orderHistory

sealed class OrderHistoryEvents {
    data class GetOrderHistory(val id : Int) : OrderHistoryEvents()

}