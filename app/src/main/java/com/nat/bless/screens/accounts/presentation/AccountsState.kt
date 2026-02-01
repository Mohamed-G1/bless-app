package com.nat.bless.screens.accounts.presentation

import com.nat.bless.screens.accounts.models.AccountsResponse

data class AccountsState(
    val isLoading: Boolean = false,
    val errorMessage: String = "",
    val customer_id: Int = 0,
    val model: AccountsResponse? = null
)
