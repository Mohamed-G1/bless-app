package com.nat.greco.screens.receviceStock.presentation

sealed class ReceiveStockEvents {

    data class ConfirmReceiveStock(val id: Int) : ReceiveStockEvents()
}