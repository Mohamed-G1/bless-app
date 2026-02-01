package com.nat.bless.screens.priceList.presentation

sealed class PriceListEvents {
    data class GetCustomerId(val customerId: Int) : PriceListEvents()
}