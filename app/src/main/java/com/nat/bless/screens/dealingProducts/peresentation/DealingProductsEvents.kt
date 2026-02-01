package com.nat.bless.screens.dealingProducts.peresentation


sealed class DealingProductsEvents {
    data class CustomerIDChanged(val customerId : Int) : DealingProductsEvents()

}