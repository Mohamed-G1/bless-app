package com.nat.bless.screens.confirmOrder

sealed class ConfirmOrderEvents {
    data class OrderAndCustomerIdsChanged(val id: Int, val customerid : Int) : ConfirmOrderEvents()
    data object ConfirmOrder : ConfirmOrderEvents()
    data object NavigationComplete : ConfirmOrderEvents()
}