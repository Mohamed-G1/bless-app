package com.nat.greco.screens.confirmOrder

sealed class ConfirmOrderEvents {
    data class OrderIdChanged(val id: Int) : ConfirmOrderEvents()
    data object ConfirmOrder : ConfirmOrderEvents()
}