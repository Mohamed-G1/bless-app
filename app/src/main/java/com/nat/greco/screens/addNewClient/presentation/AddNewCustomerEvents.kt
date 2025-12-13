package com.nat.greco.screens.addNewClient.presentation

sealed class AddNewCustomerEvents {
    data class NameChanged(val name: String) : AddNewCustomerEvents()
    data class PhoneChanged(val phone: String) : AddNewCustomerEvents()

    data class CountryChanged(val country: Int) : AddNewCustomerEvents()
    data class CityChanged(val city: Int) : AddNewCustomerEvents()
    data class AreaChanged(val area: Int) : AddNewCustomerEvents()
    data class StateChanged(val state: Int) : AddNewCustomerEvents()



    data class EmailChanged(val email: String) : AddNewCustomerEvents()
    data class ContractChanged(val contract: String) : AddNewCustomerEvents()
    data class LocationChanged(val lat: String, val long: String, val name : String) : AddNewCustomerEvents()
    data class AddressChanged(val address: String) : AddNewCustomerEvents()
    data object Submit : AddNewCustomerEvents()
}