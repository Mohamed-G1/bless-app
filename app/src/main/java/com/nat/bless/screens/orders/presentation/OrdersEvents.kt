package com.nat.bless.screens.orders.presentation

sealed class OrdersEvents {

data object GetOrdersEvent : OrdersEvents()
    data object GetReturnsEvent : OrdersEvents()
    data object OnResetClicked : OrdersEvents()
    data class MonthSelected(val month: Int, val year: Int) : OrdersEvents()

}