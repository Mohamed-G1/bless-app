package com.nat.greco.screens.addNewOrders.presentation.chooseCustomer

import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse

data class ChooseCustomerState(
    val isLoading: Boolean = false,
    val error : String = "",
    val customers : List<CustomerResponse> = listOf()
)
