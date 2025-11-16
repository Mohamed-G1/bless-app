package com.nat.greco.screens.addNewOrders.presentation

import com.nat.greco.screens.addNewOrders.models.NewProductsResponse
import com.nat.greco.screens.addNewOrders.models.addTocart.AddToCartResponse

data class NewProductsState(
    val isLoading: Boolean = false,
    val navigateBack: Boolean = false,
    val navigateToConfirmOrder: Boolean = false,
    val errorMessage: String = "",
    val customerId: Int? = null,
    val model: NewProductsResponse? = null,
    val addToCartModel: AddToCartResponse? = null
)
