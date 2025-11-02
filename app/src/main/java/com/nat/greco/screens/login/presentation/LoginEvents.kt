package com.nat.greco.screens.login.presentation

sealed class LoginEvents {
    data class SubmitUser(
        val phone: String,
        val password: String
    ) : LoginEvents()

  data class LocationFetched(
        val lat: String,
        val long: String
    ) : LoginEvents()

    data class PhoneNumberChanged(val phone: String) : LoginEvents()
    data class PasswordChanged(val password: String) : LoginEvents()
    data object NavigationComplete : LoginEvents()
}