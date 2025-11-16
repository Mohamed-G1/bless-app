package com.nat.greco.screens.clients

import com.nat.greco.screens.addNewClient.domain.models.CustomerResponse

data class CutomersState(
    val isLoading: Boolean = false,
    val error : String = "",
    val customers : List<CustomerResponse> = listOf()
)
