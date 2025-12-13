package com.nat.greco.screens.orders.presentation

sealed class OrdersEvents {

data object GetOrdersEvent : OrdersEvents()
    data object GetReturnsEvent : OrdersEvents()
}