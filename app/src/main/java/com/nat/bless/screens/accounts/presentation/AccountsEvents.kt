package com.nat.bless.screens.accounts.presentation

sealed class AccountsEvents {
    data class CustomerIdChanged(val customerId: Int) : AccountsEvents()
}