package com.nat.bless.screens.orders.presentation

sealed class OrdersEvents {

data object GetOrdersEvent : OrdersEvents()
    data object GetReturnsEvent : OrdersEvents()
}