package com.nat.greco.screens.addNewOrders.presentation

import com.nat.greco.screens.addNewOrders.models.SelectedUnit

sealed class NewProductsEvents {
    data class AddToCart(val selectedUnits: List<SelectedUnit>) : NewProductsEvents()
    data class CustomerIdChanged(val customerId: Int) : NewProductsEvents()
}