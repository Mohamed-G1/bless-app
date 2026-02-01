package com.nat.bless.screens.addNewOrders.presentation

import com.nat.bless.screens.addNewOrders.models.SelectedUnit

sealed class NewProductsEvents {
    data class AddToCart(val selectedUnits: List<SelectedUnit>) : NewProductsEvents()
    data class CustomerIdChanged(val customerId: Int) : NewProductsEvents()
    data object NavigationCompleted: NewProductsEvents()
}