package com.nat.greco.screens.priceList.presentation

sealed class PriceListEvents {
    data class GetCustomerId(val customerId: Int) : PriceListEvents()
}