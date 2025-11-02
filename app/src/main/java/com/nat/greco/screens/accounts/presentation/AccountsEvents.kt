package com.nat.greco.screens.accounts.presentation

sealed class AccountsEvents {
    data class CustomerIdChanged(val customerId: Int) : AccountsEvents()
}