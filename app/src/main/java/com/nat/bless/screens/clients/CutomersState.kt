package com.nat.bless.screens.clients

import com.nat.bless.screens.addNewClient.domain.models.CustomerResponse

data class CutomersState(
    val isLoading: Boolean = false,
    val error : String = "",
    val customers : List<CustomerResponse> = listOf()
)
