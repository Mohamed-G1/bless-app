package com.nat.bless.screens.addNewOrders.presentation.chooseCustomer

import com.nat.bless.screens.addNewClient.domain.models.CustomerResponse

data class ChooseCustomerState(
    val isLoading: Boolean = false,
    val error : String = "",
    val customers : List<CustomerResponse> = listOf()
)
