package com.nat.greco.screens.addNewClient.presentation

sealed class AddNewCustomerEvents {
    data class NameChanged(val name: String) : AddNewCustomerEvents()
    data class PhoneChanged(val phone: String) : AddNewCustomerEvents()
    data class EmailChanged(val email: String) : AddNewCustomerEvents()
    data class ContractChanged(val contract: String) : AddNewCustomerEvents()
    data class AddressChanged(val address: String) : AddNewCustomerEvents()
    data object Submit : AddNewCustomerEvents()
}