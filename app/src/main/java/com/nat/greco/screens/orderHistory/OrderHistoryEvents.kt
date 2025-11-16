package com.nat.greco.screens.orderHistory

sealed class OrderHistoryEvents {
    data class GetOrderHistory(val id : Int) : OrderHistoryEvents()

}