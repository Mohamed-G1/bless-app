package com.nat.greco.screens.orderHistory

sealed class OrderHistoryEvents {
    data object GetOrderHistory : OrderHistoryEvents()

}